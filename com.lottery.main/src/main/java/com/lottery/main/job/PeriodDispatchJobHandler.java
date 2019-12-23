package com.lottery.main.job;

import com.common.util.RPCResult;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryPeriodService;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * 派奖
 */
@JobHandler(value = "PeriodDispatchJobHandler")
@Slf4j
@Component
public class PeriodDispatchJobHandler extends IJobHandler {

    @Resource
    private Executor executor;

    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();

        RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
        List<ProxyDto> list = new ArrayList<>();
        if (listRPCResult.getSuccess()) {
            list = listRPCResult.getData();
        }

        CountDownLatch proxyCountDown = new CountDownLatch(list.size());
        for (ProxyDto dto : list) {
            executor.execute(() -> {
                try {
                    for (LotteryCategoryEnum category : lotteryCategory) {
                        if (category.getParent() != null && category.getPrivateLottery() == YesOrNoEnum.YES) {
                            dispatchPeriod(category, dto.getId());
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
                    dispatchPeriod(category, null);
                    countDownLatch.countDown();
                });
            } else {
                countDownLatch.countDown();
            }
        }
        countDownLatch.await();
        return SUCCESS;
    }


    private void dispatchPeriod(LotteryCategoryEnum category, String proxyId) {
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

        lotteryPeriodService.dispatchPeriod(category, proxyId, period.getId());

        LotteryPeriod upPeriod = new LotteryPeriod();
        upPeriod.setDispatchStatus(YesOrNoEnum.YES.getValue());
        upPeriod.setId(period.getId());
        upPeriod.setProxyId(proxyId);
        upPeriod.setLotteryType(category.getValue());

        lotteryPeriodService.save(upPeriod);
    }
}
