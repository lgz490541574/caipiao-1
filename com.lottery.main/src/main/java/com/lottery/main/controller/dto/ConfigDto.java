package com.lottery.main.controller.dto;

import com.common.util.AbstractDTO;
import lombok.Data;

/**
 * 配置管理
 */
@Data
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
    private String porxyId;

    /**
     * 号码限制
     */
    private Integer codeLimit;

}
