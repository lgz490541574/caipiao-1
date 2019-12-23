package com.lottery.proxy.controller.dto;

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
    /***
     * 配置内容
     */
    private String content;
    /**
     * 彩种
     */
    private String lotteryType;

    /**
     * 代理商ID
     */
    private String porxyId;

    /**
     * 号码限制
     */
    private Integer codeLimit;

}
