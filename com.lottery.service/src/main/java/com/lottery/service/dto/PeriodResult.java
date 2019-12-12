package com.lottery.service.dto;

import com.lottery.domain.model.LotteryCategoryEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
/**
 * 开奖信息
 */
public class PeriodResult {
    /**
     * 中奖金额
     */
    private BigDecimal prizeMoney;
    /**
     * 订单金额
     */
    private BigDecimal orderMoney;
    /**
     * 盈利金额
     */
    private BigDecimal profileMoney;
    /**
     * 盈利率
     */
    private BigDecimal rate;

    /**
     * 开奖结果
     */
    private String result;
}