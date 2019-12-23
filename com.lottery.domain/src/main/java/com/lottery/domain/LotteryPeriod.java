package com.lottery.domain;

import com.common.annotation.QueryField;
import com.common.mongo.QueryType;
import com.common.util.AbstractBaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 彩期实体
 */
@Data
@CompoundIndexes(
        {
                @CompoundIndex(name = "uniqueIndex", def = "{'code':1}", unique = true)
        })
public class LotteryPeriod extends AbstractBaseEntity {

    /**
     * 彩种
     */
    @Transient
    private Integer lotteryType;
    /**
     * 是否私彩
     */
    @Transient
    private Integer privateLottery;
    /**
     * 代理商标识
     */
    @Transient
    private String proxyId;
    /**
     * 期号
     */
    private String code;

    /**
     * 开奖结果
     */
    private String result;

    /**
     * 开奖时间
     */
    private Date resultDate;

    /**
     * 开奖时间文本
     */
    private String resultDateStr;

    /**
     * 是否开奖 1 开奖 2否
     */
    private Integer openStatus;
    /**
     * 是否结算
     */
    private Integer settleStatus;
    /**
     * 下注截止时间
     */
    private Date endOrderTime;
    /**
     * 派彩状态
     */
    private Integer dispatchStatus;

    /**
     * 该期号总下注金额
     */
    private BigDecimal orderMoney;
    /**
     * 所有中奖金额
     */
    private BigDecimal money;
    /**
     * 盈利金额
     */
    private BigDecimal profileMoney;
    /**
     * 盈利率
     */
    private BigDecimal rate;
    /**
     * 预生成开奖结果号码
     */
    private String[] reserveCode;
    /**
     * 开奖方式  1 预生成号码开奖  2 自动开奖
     */
    private Integer resultStatus;
    /**
     * 小于下注截止时间
     */
    @Transient
    @QueryField(name = "endOrderTime", type = QueryType.LTE)
    private Date minEndOrderTime;
    /**
     * 大于下注截止时间
     */
    @Transient
    @QueryField(name = "endOrderTime", type = QueryType.GTE)
    private Date maxEndOrderTime;

}
