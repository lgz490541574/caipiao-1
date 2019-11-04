package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDetail extends AbstractBaseEntity {
    /**
     * 主订单标识
     */
    private String orderInfoId;

    /**
     * 彩种
     */
    private Integer lotteryType;
    /**
     * 玩法
     */
    private String playType;

    /**
     * 订单详情
     */
    private List<TicketInfo> tickets;

    /**
     * 注数
     */
    private Integer count;
    /**
     * 倍投
     */
    private Integer times;

    /**
     * 期号
     */
    private String periodCode;
    /**
     * 开奖结果
     */
    private String peroidResult;

    /**
     * 是否中奖 1 是 2 否
     */
    private Integer status;

    /**
     * 下单金额
     */
    private BigDecimal orderMoney;

    /**
     * 中奖金额
     */
    private BigDecimal money;
    /**
     * 盈亏金额
     */
    private BigDecimal profileMoney;


}
