package com.lottery.main.job;

import com.common.util.RPCResult;
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
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
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

/**
 * 算奖
 */
@JobHandler(value = "PeriodSettlePrizeJobHandler")
@Slf4j
@Component
public class PeriodSettlePrizeJobHandler extends IJobHandler {

    @Resource
    private Executor executor;

    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;

    @Resource
    private LotteryPeriodService lotteryPeriodService;


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
        CountDownLatch proxyCountDown = new CountDownLatch(list.size());
        for (ProxyDto dto : list) {
            executor.execute(() -> {
                try {
                    for (LotteryCategoryEnum category : lotteryCategory) {
                        if (category.getParent() != null && category.getPrivateLottery() == YesOrNoEnum.YES) {
                            doSettle(category, dto.getId(), allConfig.get(category));
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
                    doSettle(category, null, allConfig.get(category));
                    countDownLatch.countDown();
                });
            } else {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
        return SUCCESS;
    }


    private void doSettle(LotteryCategoryEnum category, String proxyId, Map<String, JSONArray> configArray) {
        LotteryPeriod queryPeriod = new LotteryPeriod();
        queryPeriod.setLotteryType(category.getValue());
        queryPeriod.setOpenStatus(YesOrNoEnum.YES.getValue());
        queryPeriod.setSettleStatus(YesOrNoEnum.NO.getValue());
        queryPeriod.setProxyId(proxyId);
        Page<LotteryPeriod> periodPage = lotteryPeriodService.queryByPage(queryPeriod, PageRequest.of(0, 1));
        if (!periodPage.hasContent()) {
            return;
        }
        LotteryPeriod period = periodPage.getContent().get(0);
        //订单结算
        PeriodResult periodResult = lotteryPeriodService.doSettle(category, proxyId, period.getId(), period.getResult(), configArray);

        LotteryPeriod upPeriod = new LotteryPeriod();
        upPeriod.setId(period.getId());
        upPeriod.setProxyId(proxyId);
        upPeriod.setLotteryType(category.getValue());
        upPeriod.setSettleStatus(YesOrNoEnum.YES.getValue());
        upPeriod.setOrderMoney(periodResult.getOrderMoney());
        upPeriod.setMoney(periodResult.getPrizeMoney());
        BigDecimal profileMoney = periodResult.getOrderMoney().subtract(periodResult.getPrizeMoney());
        if(profileMoney.compareTo(BigDecimal.ZERO)==0){
            upPeriod.setRate(BigDecimal.ZERO);
        }
        else{
            BigDecimal rate = profileMoney.divide(periodResult.getOrderMoney(), 2, BigDecimal.ROUND_HALF_DOWN);
            upPeriod.setRate(rate);
        }

        upPeriod.setProfileMoney(profileMoney);

        lotteryPeriodService.save(upPeriod);
    }


}
