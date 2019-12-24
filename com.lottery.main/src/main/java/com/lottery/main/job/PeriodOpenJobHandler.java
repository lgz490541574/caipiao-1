package com.lottery.main.job;

import com.common.util.RPCResult;
import com.common.util.model.OrderTypeEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.Config;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.PeriodConfig;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.main.util.ILotteryResultService;
import com.lottery.main.util.LotteryResultUtils;
import com.lottery.service.ConfigService;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderService;
import com.lottery.service.PeriodConfigService;
import com.lottery.service.dto.PeriodResult;
import com.lottery.service.exception.PreBuildPeriodException;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 开奖
 */
@JobHandler(value = "PeriodOpenJobHandler")
@Slf4j
@Component
public class PeriodOpenJobHandler extends IJobHandler {

    @Resource
    private Executor executor;
    @Resource
    private LotteryResultUtils lotteryResultUtils;

    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    @Resource
    private OrderService orderService;

    @Resource
    private ConfigService configService;

    @Resource
    private PeriodConfigService periodConfigService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();

        RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
        List<ProxyDto> list = new ArrayList<>();
        if (listRPCResult.getSuccess()) {
            list = listRPCResult.getData();
        }


        CountDownLatch proxyCountDown = new CountDownLatch(list.size());


        //输赢配置
        Map<String, PeriodConfig> proxyPeriodConfigs = new HashMap<>();
        PeriodConfig periodConfig = new PeriodConfig();
        periodConfig.setStatus(YesOrNoEnum.YES.getValue());
        Page<PeriodConfig> periodConfigs = periodConfigService.queryByPage(periodConfig, PageRequest.of(0, 100));

        for (PeriodConfig config : periodConfigs.getContent()) {
            proxyPeriodConfigs.put(config.getProxyId(), config);
        }
        Map<LotteryCategoryEnum, Map<String, JSONArray>> allConfig = new HashMap<>();

        List<Config> configs = configService.queryAll();
        for (LotteryCategoryEnum category : LotteryCategoryEnum.values()) {
            if (category.getParent() != null) {
                continue;
            }
            Map<String, JSONArray> lotteryCfg = null;
            lotteryCfg = allConfig.get(category.name());
            if (lotteryCfg == null) {
                lotteryCfg = new HashMap<>();
            }
            for (Config cfg : configs) {
                if (cfg.getLotteryType().equals(category.name())) {
                    lotteryCfg.put(cfg.getKey(), JSONArray.fromObject(cfg.getContent()));
                } else {
                    continue;
                }
            }
            allConfig.put(category, lotteryCfg);
        }
        for (ProxyDto dto : list) {
            executor.execute(() -> {
                try {
                    for (LotteryCategoryEnum category : lotteryCategory) {
                        if (category.getParent() != null && category.getPrivateLottery() == YesOrNoEnum.YES) {
                            LotteryPeriod lotteryResult = lotteryPeriodService.findLastNotOpenResult(category, dto.getId());
                            if (new Date().before(lotteryResult.getResultDate())) {
                                continue;
                            }
                            List<OrderInfo> orders = new ArrayList<>();
                            OrderInfo queryOrder = new OrderInfo();
                            queryOrder.setProxyId(dto.getId());
                            queryOrder.setPeriodCode(lotteryResult.getCode());
                            queryOrder.setPayStatus(YesOrNoEnum.YES.getValue());
                            Page<OrderInfo> orderPage = orderService.queryByPage(queryOrder, PageRequest.of(0, 100));
                            while (orderPage != null && orderPage.hasContent()) {
                                orders.addAll(orderPage.getContent());
                                if (orderPage.hasNext()) {
                                    orderPage = orderService.queryByPage(queryOrder, orderPage.nextPageable());
                                } else {
                                    orderPage = null;
                                }
                            }
                            int count = 0;
                            while (true) {
                                try {
                                    PeriodResult bestRatePeriodCode = null;
                                    List<PeriodResult> resultList = lotteryPeriodService.caculateResult(category, orders, allConfig.get(category));
                                    if (orders.size() > 0) {
                                        bestRatePeriodCode = lotteryPeriodService.getBestRatePeriodCode(proxyPeriodConfigs.get(dto.getId()), resultList, category);
                                    } else {
                                        bestRatePeriodCode = resultList.get(0);
                                    }
                                    LotteryPeriod period = new LotteryPeriod();
                                    period.setLotteryType(category.getValue());
                                    period.setId(lotteryResult.getId());
                                    period.setResult(bestRatePeriodCode.getResult());
                                    period.setOpenStatus(YesOrNoEnum.YES.getValue());
                                    period.setProxyId(dto.getId());
                                    lotteryPeriodService.save(period);
                                    lotteryResultUtils.pushResultToMq(category, period.getProxyId(), lotteryResult.getCode(), bestRatePeriodCode.getResult());
                                    break;
                                } catch (PreBuildPeriodException e) {
                                    count++;
                                    log.error("开奖失败 category:" + category.getName() + " proxyId:" + dto.getId() + " proxyName:" + dto.getName());
                                    if (count == 10) {
                                        log.error("开奖次数超限 category:" + category.getName() + " proxyId:" + dto.getId() + " proxyName:" + dto.getName());
                                        break;
                                    }
                                }

                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("PeriodOpenJobHandler.execute.error proxy={}", dto.getName(), e);
                }
                proxyCountDown.countDown();
            });
        }
        proxyCountDown.await();

        CountDownLatch countDownLatch = new CountDownLatch(lotteryCategory.length);
        for (LotteryCategoryEnum category : lotteryCategory) {
            if (category.getParent() != null && category.getPrivateLottery() == YesOrNoEnum.NO) {
                executor.execute(() -> {
                    ILotteryResultService resultService = lotteryResultUtils.findResultService(category);
                    if (resultService != null) {
                        try {
                            resultService.syncLotteryResult();
                        } catch (Exception e) {
                            log.error("syncLotteryResult.error", e);
                        }
                    }
                    countDownLatch.countDown();
                });
            } else {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
        return SUCCESS;
    }


}
