package com.lottery.domain.util;

import lombok.Data;

@Data
public class IssureRule {
    /**
     * 总期数
     */
    Integer count;
    /**
     * 开始时间
     */
    Integer startHoure;
    /**
     * 开始分数
     */
    Integer startMins;
    /**
     * 时间间隔
     */
    Integer longTime;

    public IssureRule(Integer count, Integer startHoure, Integer startMins, Integer longTime) {
        this.count = count;
        this.startHoure = startHoure;
        this.startMins = startMins;
        this.longTime = longTime;
    }
}
