package com.lottery.main.controller.dto;

import com.common.util.AbstractDTO;

/**
 * 配置管理
 */
public class ConfigDto extends AbstractDTO {
    /**
     * key
     */
    private String key;
    /**
     * 配置名称
     */
    private String name;
    /***
     * 配置内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 游戏类型
     */
    private String gameType;

    /**
     * 代理商ID
     */
    private Long porxyId;

    /**
     * 号码限制
     */
    private Integer codeLimit;

    public Integer getCodeLimit() {
        return codeLimit;
    }

    public void setCodeLimit(Integer codeLimit) {
        this.codeLimit = codeLimit;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getPorxyId() {
        return porxyId;
    }

    public void setPorxyId(Long porxyId) {
        this.porxyId = porxyId;
    }

}
