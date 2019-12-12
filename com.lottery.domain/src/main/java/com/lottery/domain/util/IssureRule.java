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
     * 开始秒数
     */
    Integer startSeconds;
    /**
     * 时间间隔
     */
    Integer longTime;

    String[] times;

    private String codeFormat;
    public IssureRule(Integer count, Integer startHoure, Integer startMins, Integer longTime) {
        this.count = count;
        this.startHoure = startHoure;
        this.startMins = startMins;

        this.longTime = longTime;
    }

    public IssureRule(Integer count, Integer startHoure, Integer startMins, Integer startSeconds, Integer longTime) {
        this.count = count;
        this.startHoure = startHoure;
        this.startMins = startMins;
        this.startSeconds = startSeconds;
        this.longTime = longTime;
        this.startSeconds = startSeconds;
    }

    public IssureRule(Integer count, Integer startHoure, Integer startMins, Integer startSeconds, Integer longTime,String codeFormat) {
        this.count = count;
        this.startHoure = startHoure;
        this.startMins = startMins;
        this.startSeconds = startSeconds;
        this.longTime = longTime;
        this.startSeconds = startSeconds;
        this.codeFormat=codeFormat;
    }
    public IssureRule(Integer count, Integer startHoure, Integer startMins, Integer startSeconds, Integer longTime,String codeFormat,String[] times) {
        this.count = count;
        this.startHoure = startHoure;
        this.startMins = startMins;
        this.startSeconds = startSeconds;
        this.longTime = longTime;
        this.startSeconds = startSeconds;
        this.codeFormat=codeFormat;
        this.times=times;
    }
}
