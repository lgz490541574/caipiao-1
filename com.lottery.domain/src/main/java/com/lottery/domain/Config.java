package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

/**
 * 配置信息
 */
@Data
public class Config extends AbstractBaseEntity {
    /**
     * 代理商
     */
    private String proxyId;
    private String key;
    private String name;
    private String content;
    private String remark;
    private String gameType;
    /**
     * 号码限制
     */
    private Integer codeLimit;

}
