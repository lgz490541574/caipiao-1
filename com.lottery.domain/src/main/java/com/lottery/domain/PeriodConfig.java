package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 期号盈利配置
 */
@Data
public class PeriodConfig extends AbstractBaseEntity {
    /**
     * 代理商id
     */
    private String proxyId;
    /**
     * 最小盈利比
     */
    private String minProfit;
    /**
     * 最大盈利比
     */
    private String maxProfit;
    /**
     * 最小亏损比
     */
    private String minLoss;
    /**
     * 最大亏损比
     */
    private String maxLoss;
    /**
     * 最大亏损金额
     */
    private BigDecimal maxLossMoney;
    /**
     * 配置状态  1 盈利模式  2 亏损模式 3 随机模式
     */
    private Integer status;
    /**
     * 盈利  权重  例如：4  满值为10  则表示  40%盈利几率  为 10 则为100%盈利  只在随机模式生效
     */
    private Integer weight;
}
