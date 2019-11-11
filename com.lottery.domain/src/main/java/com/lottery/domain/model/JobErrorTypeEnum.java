package com.lottery.domain.model;

import com.common.util.IGlossary;

public enum JobErrorTypeEnum implements IGlossary {
    Issure("彩期", 1);

    private String name;
    private Integer value;

    JobErrorTypeEnum(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public Integer getValue() {
        return this.value;
    }
}
