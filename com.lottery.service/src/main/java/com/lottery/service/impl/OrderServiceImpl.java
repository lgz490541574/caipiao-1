package com.lottery.service.impl;

import com.account.rpc.AccountRPCService;
import com.account.rpc.dto.AccountDto;
import com.account.rpc.dto.BizTypeEnum;
import com.account.rpc.dto.InvertBizDto;
import com.common.exception.BizException;
import com.common.mongo.AbstractMongoService;
import com.common.util.RPCResult;
import com.common.util.StringUtils;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderDetail;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.TicketInfo;
import com.lottery.domain.dto.OrderCode;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.OrderSplitTools;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderDetailService;
import com.lottery.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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


    private void createDetail(String orderInfoId, LotteryCategoryEnum lotteryCategory,String playType, String periodCode, TicketInfo[] tickets, Integer times) {
        OrderDetail detail = new OrderDetail();
        detail.setOrderInfoId(orderInfoId);
        detail.setPeriodCode(periodCode);
        detail.setTickets(Arrays.asList(tickets));
        detail.setLotteryType(lotteryCategory.getValue());
        detail.setCount(tickets.length);
        detail.setOrderMoney(BigDecimal.valueOf(tickets.length).multiply(BigDecimal.valueOf(times)));
        detail.setPlayType(playType);
        orderDetailService.insert(detail);
    }

    /**
     * 下单
     *
     * @param proxyId    代理商
     * @param pin        用户
     * @param type       类型
     * @param periodId   期号
     * @param codes      下单号码
     * @param times      部投
     * @param orderMoney 下单金额
     * @param chaseMark  追号标记 为空时则没有追号
     * @param prizeStop  中奖即停 追号标记不为空时则 该参数不能为空
     */
    @Override
    @Transactional
    public BigDecimal createOrder(String proxyId, String pin, LotteryCategoryEnum type, String periodId, OrderCode[] codes, Integer times, BigDecimal orderMoney, String chaseMark, YesOrNoEnum prizeStop) {
        List<List<TicketInfo>> listTicket = new ArrayList<>();
        Map<List<TicketInfo>,String> types=new HashMap<>();
        int count = 0;
        for (OrderCode dto : codes) {
            List<TicketInfo> e = OrderSplitTools.doSplit(type, dto.getPlayType(), dto.getLotteryCode());
            types.put(e,dto.getPlayType());
            //计算注数
            count = count + e.size();
            listTicket.add(e);
        }
        if (count == 0) {
            throw new BizException("order.error", "下单失败，下单注数必须大于0");
        }

        if (StringUtils.isNotBlank(chaseMark)) {
            if (prizeStop == null) {
                throw new BizException("order.error", "下单失败，追号时参数 prizeStop 不能为空");
            }
        }

        //单注金额为1元 总金额必须等于下单金额(money)
        BigDecimal totalMoney = BigDecimal.ONE.multiply(BigDecimal.valueOf(count));
        if (!totalMoney.equals(orderMoney)) {
            throw new BizException("order.error", "下单失败，下单金额错误");
        }

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

        LotteryPeriod period=lotteryPeriodService.findById(type,proxyId,periodId);
        OrderInfo order = new OrderInfo();

        order.setPin(pin);
        order.setPeriodId(periodId);
        order.setCodes(codes);
        order.setProxyId(proxyId);
        order.setLotteryType(type.getValue());
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
        for(List<TicketInfo> listItem: listTicket){
            createDetail(order.getId(),type,types.get(listItem),period.getCode(),listItem.toArray(new TicketInfo[listItem.size()]),times);
        }

        InvertBizDto dto = new InvertBizDto();
        dto.setPin(pin);
        dto.setAmount(orderMoney);
        dto.setBizId(order.getId());
        dto.setBizType(BizTypeEnum.caipiao);
        dto.setOperator(pin);
        dto.setRemark("pay lottery order");
        RPCResult<BigDecimal> payResult = null;
        try {
            payResult = accountRPCService.invertBiz(dto);
            if (payResult.getSuccess()) {
                String orderId = order.getId();
                order = new OrderInfo();
                order.setId(orderId);
                order.setPayStatus(YesOrNoEnum.YES.getValue());
                save(order);
                return payResult.getData();
            }
        } catch (Exception e) {
            log.error("order.pay.error",e);
            throw new BizException("order.error", "网络错误，请稍后再试");
        }
        //账户余额不足
        if (!payResult.getSuccess() && payResult.getCode().equals("account.not.enough")) {
            throw new BizException(payResult.getCode(), payResult.getMessage());
        }
        log.error("order.pay.error code={},message={}", new String[]{payResult.getCode(), payResult.getMessage()});
        throw new BizException("order.error", "网络错误，请稍后再试");

    }


}
