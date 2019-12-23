package com.lottery.service.impl;

import com.account.rpc.AccountRPCService;
import com.account.rpc.dto.AccountDto;
import com.account.rpc.dto.BizTypeEnum;
import com.account.rpc.dto.InvokeBizDto;
import com.common.exception.BizException;
import com.common.mongo.AbstractMongoService;
import com.common.util.RPCResult;
import com.common.util.StringUtils;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderDetail;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.TicketInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.IPlayType;
import com.lottery.domain.util.OrderSplitTools;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderDetailService;
import com.lottery.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl extends AbstractMongoService<OrderInfo> implements OrderService {

    //现金账户
    static Integer tokenType = 1;

    @Reference
    private AccountRPCService accountRPCService;

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    @Resource
    private OrderDetailService orderDetailService;

    @Override
    protected Class getEntityClass() {
        return OrderInfo.class;
    }

    private void createDetail(String orderInfoId, LotteryCategoryEnum lotteryCategory, String playType, String periodCode, List<TicketInfo> tickets, Integer times) {
        OrderDetail detail = new OrderDetail();
        detail.setOrderInfoId(orderInfoId);
        detail.setPeriodCode(periodCode);
        detail.setTickets(tickets);
        detail.setLotteryType(lotteryCategory.getValue());
        detail.setCount(tickets.size());
        detail.setOrderMoney(BigDecimal.valueOf(tickets.size()).multiply(BigDecimal.valueOf(times)));
        detail.setPlayType(playType);
        orderDetailService.insert(detail);
    }

    private void delDetailByOrderInfoId(String orderInfoId){
        Query query = new Query();
        Criteria criteria = Criteria.where("orderInfoId").is(orderInfoId);
        query.addCriteria(criteria);
        primaryTemplate.remove(query,OrderDetail.class);
    }


    /**
     * 下单
     *
     * @param proxyId    代理商
     * @param pin        用户
     * @param type       类型
     * @param periodCode 期号
     * @param codeList   下单号码
     * @param times      部投
     * @param chaseMark  追号标记 为空时则没有追号
     * @param prizeStop  中奖即停 追号标记不为空时则 该参数不能为空
     */
    @Override
    @Transactional
    public BigDecimal createOrder(String proxyId, String pin, LotteryCategoryEnum type, String playType, String periodCode, List<String[]> codeList, Integer times, String chaseMark, YesOrNoEnum prizeStop) {
        LotteryPeriod period = lotteryPeriodService.findByCode(type, proxyId, periodCode);
        if (period == null) {
            throw new BizException("createOrder.error", "下单失败，期号不存在");
        }
        if (new Date().after(period.getEndOrderTime())) {
            throw new BizException("create.order.periodCode.end.time.error", "下单失败,彩期已截止下注");
        }
        IPlayType play = OrderSplitTools.getPlay(type, playType);
        int count = 0;
        Map<String[], List<TicketInfo>> ordersMap = new HashMap<>();

        for (String[] codes : codeList) {
            List<TicketInfo> ticketInfos = play.getOrderSplit().doSplit(type, playType, codes);
            for (TicketInfo info : ticketInfos) {
                info.setTimes(times);
            }
            ordersMap.put(codes, ticketInfos);
            count = count + ticketInfos.size();
        }
        //折单
        if (count == 0) {
            throw new BizException("order.error", "下单失败，下单注数必须大于0");
        }
        if (StringUtils.isNotBlank(chaseMark)) {
            if (prizeStop == null) {
                throw new BizException("order.error", "下单失败，追号时参数 prizeStop 不能为空");
            }
        }

        //单注金额为1元 总金额必须等于下单金额(money)
        BigDecimal orderMoney = BigDecimal.ONE.multiply(BigDecimal.valueOf(count)).multiply(BigDecimal.valueOf(times));

        RPCResult<AccountDto> accountResult = accountRPCService.findAccount(pin, tokenType);
        if (accountResult.getSuccess()) {
            //账户余额是否不足
            if (accountResult.getData().getAmount().compareTo(orderMoney) < 0) {
                throw new BizException("order.error", "下单失败，余额不足");
            }
        } else {
            log.error("accountRPCService.findAccount.error code={},message={}", new String[]{accountResult.getCode(), accountResult.getMessage()});
            throw new BizException("order.error", "网络错误,请稍后再试");
        }

        OrderInfo order = new OrderInfo();

        order.setPin(pin);
        order.setPeriodId(period.getId());
        order.setPeriodCode(period.getCode());
        order.setCodeList(codeList);
        order.setProxyId(proxyId);
        order.setLotteryType(type.getValue());
        order.setPlayType(playType);
        order.setOrderMoney(orderMoney);
        order.setPayStatus(YesOrNoEnum.NO.getValue());
        //追号参数
        if (StringUtils.isNotBlank(chaseMark)) {
            //追号标记
            order.setChaseMark(chaseMark);
            //中奖即停
            order.setPrizeStop(prizeStop.getValue());
        }
        //支付错误次数
        order.setPayErrorTimes(0);

        //账本同步错误次数为0
        order.setAccountErrorTimes(0);

        //存入订单
        insert(order);
        for (String[] codes : codeList) {
            createDetail(order.getId(), type, playType, period.getCode(), ordersMap.get(codes), times);
        }
        InvokeBizDto dto = new InvokeBizDto();
        dto.setPin(pin);
        dto.setTokenType(tokenType);
        dto.setAmount(orderMoney);
        dto.setBizId(order.getId());
        dto.setBizType(BizTypeEnum.lottery);
        dto.setSubBiz("下单");
        dto.setOperator("system");
        dto.setRemark("彩票下单");
        RPCResult<BigDecimal> payResult = null;
        try {
            payResult = accountRPCService.invoke(dto);
            if (payResult.getSuccess()) {
                String orderId = order.getId();
                order = new OrderInfo();
                order.setId(orderId);
                order.setPayStatus(YesOrNoEnum.YES.getValue());
                save(order);
                return payResult.getData();
            }
        } catch (Exception e) {
            log.error("order.pay.error", e);
            throw new BizException("order.error", "网络错误，请稍后再试");
        }
        //账户余额不足
        if (!payResult.getSuccess() && payResult.getCode().equals("account.not.enough")) {
            delById(order.getId());
            delDetailByOrderInfoId(order.getId());
            throw new BizException(payResult.getCode(), payResult.getMessage());
        }
        log.error("order.pay.error code={},message={}", new String[]{payResult.getCode(), payResult.getMessage()});
        throw new BizException("order.error", "网络错误，请稍后再试");
    }
}
