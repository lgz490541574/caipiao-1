package com.lottery.domain.model;

import com.common.util.IGlossary;

public enum ProfitEnum implements IGlossary {

    PROFIT("盈利模式",1),
    LOSS("亏损模式",2),
    RANDOM("随机模式",3)
    ;

    ProfitEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 名字
     */
    private String name;
    /**
     * 数据库存值
     */
    private Integer value;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
