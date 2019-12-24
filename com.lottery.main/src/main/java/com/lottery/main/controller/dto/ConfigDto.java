package com.lottery.main.controller.dto;

import com.common.util.AbstractDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 配置管理
 */
@Data
@ApiModel("配置")
public class ConfigDto extends AbstractDTO {
    /**
     * key
     */
    @ApiModelProperty("key")
    private String key;
    /**
     * 配置名称
     */
    @ApiModelProperty("名称")
    private String name;
    /***
     * 配置内容
     */
    @ApiModelProperty("配置内容")
    private String content;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;
    /**
     * 游戏类型
     */
    @ApiModelProperty("游戏类型")
    private String gameType;

    /**
     * 代理商ID
     */
    @ApiModelProperty("代理商ID")
    private String porxyId;

    /**
     * 号码限制
     */
    @ApiModelProperty("号码限制")
    private Integer codeLimit;

}
