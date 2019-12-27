package com.lottery.service.dto;

import com.common.util.AbstractDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class LotteryPeriodDto extends AbstractDTO {
    /**
     * 彩种
     */
    private Integer lotteryType;

    /**
     * 是否私彩
     */
    private Integer privateLottery;
    /**
     * 代理商标识
     */
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
}
