package com.lottery.domain;

import com.common.annotation.QueryField;
import com.common.mongo.QueryType;
import com.common.util.AbstractBaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单详情
 */
@Data
public final class OrderInfo extends AbstractBaseEntity {
    /**
     * 用户
     */
    private String pin;
    /**
     * 追号标记
     */
    private String chaseMark;

    /**
     * 中奖即停
     */
    private Integer prizeStop;
    /**
     * 代理账号
     */
    private String proxyId;
    /**
     * 种类
     */
    private Integer lotteryType;
    /**
     * 期号id
     */
    private String periodId;
    /**
     * 期号
     */
    private String periodCode;
    /**
     * 开奖结果
     */
    private String peroidResult;
    /**
     *下注号码
     */
    private List<String[]> codeList;
    /**
     * 倍投
     */
    private Integer times;
    /**
     * 注数
     */
    private Integer count;
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
    /***
     * 是否结算 1 是 2 否
     */
    private Integer settleStatus;

    /**
     * 扣款状态 1是 2 否
     */
    private Integer payStatus;
    /**
     * 支付失败次数
     */
    private Integer payErrorTimes;
    /**
     * 中奖到账状态 1是 2 否 3到账失败
     */
    private Integer accountStatus;
    /**
     * 中奖到账失败次数
     */
    private Integer accountErrorTimes;
    /**
     * 是否追号 1是 2 否
     */
    private Integer isChase;

    /**
     * 追号状态 中奖即停 1是 2 否
     */
    private Integer chaseStatus;
    /**
     * 倍投状态  1 是  2 否
     */
    private Integer doubleStatus;
    /**
     * 抽水状态 1是 2 否 3 抽水失败
     */
    private Integer commissionStatus;
    /**
     * 抽水失败次数 3次过后将不再重试
     */
    private Integer commissionErrorTimes;
    /**
     * 小于等于抽水失败次数 3次
     */
    @Transient
    @QueryField(name = "commissionErrorTimes", type = QueryType.LTE)
    private Integer maxcommissionErrorTimes;
    /**
     * 小于等于支付失败次数 3次
     */
    @Transient
    @QueryField(name = "payErrorTimes", type = QueryType.LTE)
    private Integer maxPayErrorTimes;

    /**
     * 小于等于到账失败次数 3次
     */
    @Transient
    @QueryField(name = "accountErrorTimes", type = QueryType.LTE)
    private Integer maxAccountErrorTimes;

}
