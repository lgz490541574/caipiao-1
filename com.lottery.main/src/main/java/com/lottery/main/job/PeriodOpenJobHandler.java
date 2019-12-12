package com.lottery.main.job;

import com.common.util.RPCResult;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.Config;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.main.util.ILotteryResultService;
import com.lottery.main.util.LotteryResultUtils;
import com.lottery.service.ConfigService;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderService;
import com.lottery.service.dto.PeriodResult;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();

        RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
        List<ProxyDto> list = new ArrayList<>();
        if (listRPCResult.getSuccess()) {
            list = listRPCResult.getData();
        }
        CountDownLatch proxyCountDown = new CountDownLatch(list.size());
        Map<String, List<Config>> allConfig = new HashMap<>();

        Page<Config> configs = configService.queryByPage(new Config(), PageRequest.of(0, 100));

        while (configs != null && configs.hasNext()) {
            for (Config item : configs.getContent()) {
                List<Config> listItem = null;
                if (allConfig.get(item.getProxyId()) == null) {
                    listItem = new ArrayList<>();
                }
                listItem.add(item);
                allConfig.put(item.getProxyId(), listItem);
            }
        }
        for (ProxyDto dto : list) {
            executor.execute(() -> {
                try {
                    for (LotteryCategoryEnum category : lotteryCategory) {
                        if (category.getParent() != null && category.getPrivateLottery() == YesOrNoEnum.YES) {
                            List<OrderInfo> orders = new ArrayList<>();
                            OrderInfo queryOrder = new OrderInfo();
                            queryOrder.setProxyId(dto.getId());
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
                            List<PeriodResult> resultList = lotteryPeriodService.caculateResult(category, orders, allConfig.get(dto.getId()));
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
