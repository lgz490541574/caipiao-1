package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;

/**
 * 任务异常
 */
@Data
public class JobErrorInfo extends AbstractBaseEntity {
    /**
     * 类型
     */
    private Integer jobType;
    /**
     * 参数
     */
    private String params;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 异常状态
     */
    private Integer errorTimes;
}
