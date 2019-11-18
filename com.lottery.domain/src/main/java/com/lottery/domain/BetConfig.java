package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 下注配置设置
 */
@Data
public class BetConfig extends AbstractBaseEntity {
    /**
     * 代理商
     */
    private String proxyId;
    /**
     * 单注最低下注金额
     */
    private BigDecimal minMoney;
    /**
     * 单注最高下注金额
     */
    private BigDecimal maxMoney;
    /**
     * 每期最高下注金额 超过该金额拒绝下注
     */
    private BigDecimal maxPeriodMoney;
    /**
     * 账户最低金额 低于该金额不能下注
     */
    private BigDecimal minAmount;
    /**
     * 最小充值金额 低于该金额 不能下注
     */
    private BigDecimal minChargerAmount;
}
