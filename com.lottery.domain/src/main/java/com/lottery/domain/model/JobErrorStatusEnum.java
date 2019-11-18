package com.lottery.domain.model;

import com.common.util.IGlossary;

/**
 * 异常任务状态
 */
public enum JobErrorStatusEnum implements IGlossary {
    ERROR("失败", 1),
    SUCCESS("成功", 2),
    ;

    private String name;
    private Integer value;

    JobErrorStatusEnum(String name, Integer value) {
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
