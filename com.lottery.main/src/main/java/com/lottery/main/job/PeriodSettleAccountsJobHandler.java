package com.lottery.main.job;

import com.common.util.DateUtil;
import com.common.util.RPCResult;
import com.common.util.model.OrderTypeEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.IssureRule;
import com.lottery.main.util.ILotteryResultService;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderService;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 结算
 */
@JobHandler(value = "PeriodSettleAccountsJobHandler")
@Slf4j
@Component
public class PeriodSettleAccountsJobHandler extends IJobHandler {

    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    @Resource
    private OrderService orderService;

    @Resource
    private Executor executor;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        try {

            LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();
            RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
            List<ProxyDto> list = new ArrayList<>();
            if (listRPCResult.getSuccess()) {
                list = listRPCResult.getData();
            }
            LotteryPeriod period = new LotteryPeriod();
            period.setOpenStatus(YesOrNoEnum.YES.getValue());
            period.setSettleStatus(YesOrNoEnum.NO.getValue());

            Pageable pageable = PageRequest.of(0, 10);
            Pageable orderPageable = PageRequest.of(0, 100);
            Page<LotteryPeriod> lotteryPeriods = null;
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderColumn("createTime");
            orderInfo.setOrderType(OrderTypeEnum.ASC);
            orderInfo.setPayStatus(YesOrNoEnum.YES.getValue());
            for (LotteryCategoryEnum category : lotteryCategory) {
                if (category.getParent() == null) {
                    continue;
                }
                period.setLotteryType(category.getValue());
                if (category.getPrivateLottery() == YesOrNoEnum.YES) {

                } else {
                    lotteryPeriods = lotteryPeriodService.queryByPage(period, pageable);
                    for (LotteryPeriod itemPeriod : lotteryPeriods.getContent()) {
                        orderInfo.setLotteryType(itemPeriod.getLotteryType());
                        Page<OrderInfo> orderInfoPage = orderService.queryByPage(orderInfo, orderPageable);
                        List<OrderInfo> listOrders = new ArrayList(Integer.valueOf((int) orderInfoPage.getTotalElements()));
                        while(orderInfoPage!=null){
                            listOrders.addAll(orderInfoPage.getContent());
                            if(orderInfoPage.hasNext()){
                                orderInfoPage=orderService.queryByPage(orderInfo, orderInfoPage.nextPageable());
                            }
                            else{
                                orderInfoPage=null;
                            }
                        }
                        orderService.doSettle(listOrders,itemPeriod.getResult());
                    }
                }
            }
        } catch (Exception e) {
            log.error("PeriodOpenJobHandler.error", e);
        }
        return SUCCESS;
    }
}
