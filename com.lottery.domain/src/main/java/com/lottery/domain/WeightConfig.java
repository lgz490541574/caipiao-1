package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 盈利权重配置
 */
@Data
public class WeightConfig extends AbstractBaseEntity {

    private String proxyId;
    /**
     * 输赢比例 -0.5 ~ 0.5
     */
    private BigDecimal rate;
    /**
     * 输赢金额 -500000 ~ 12000000
     */
    private BigDecimal money;
    /**
     * 随机权重 20 ~ 70
     */
    private Integer weight;
    /**
     * 状态  1 正常  2 暂停(忽略)
     */
    private Integer status;

}
