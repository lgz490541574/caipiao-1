package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 抽水配置
 */
@Data
public class CommissionConfig extends AbstractBaseEntity {
    /**
     * 代理商
     */
    private String proxyId;
    /**
     * 抽水总比
     */
    private BigDecimal total;
    /**
     * 平台抽水比
     */
    private BigDecimal sysTotal;
    /**
     * 推荐人抽水比
     */
    private BigDecimal recommendTotal;
    /**
     * 打码量  总下注量
     */
    private BigDecimal orderMoney;
    /**
     * 退水  比例
     */
    private BigDecimal backRate;
}
