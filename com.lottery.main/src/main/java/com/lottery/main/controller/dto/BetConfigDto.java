package com.lottery.main.controller.dto;

import com.common.util.AbstractDTO;

import java.math.BigDecimal;

/**
 * 下注设置dto
 */
public class BetConfigDto extends AbstractDTO {

    /**
     * 代理商id
     */
    private Long porxyId;
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

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMinChargerAmount() {
        return minChargerAmount;
    }

    public void setMinChargerAmount(BigDecimal minChargerAmount) {
        this.minChargerAmount = minChargerAmount;
    }

    public Long getPorxyId() {
        return porxyId;
    }

    public void setPorxyId(Long porxyId) {
        this.porxyId = porxyId;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getMaxPeriodMoney() {
        return maxPeriodMoney;
    }

    public void setMaxPeriodMoney(BigDecimal maxPeriodMoney) {
        this.maxPeriodMoney = maxPeriodMoney;
    }
}
