package com.lottery.domain;

import com.common.util.AbstractBaseEntity;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * 彩种配置信息
 */
@Data
public class LotteryConfig extends AbstractBaseEntity {

    /**
     * 代理商
     */
    private String proxyId;

    /**
     * 彩种类型 如LHC
     */
    private Integer lotteryType;
    /**
     * 期数备注 如 一天120期
     */
    private String remark;
    /**
     * 彩种描述 如 开奖时间
     */
    private String content;
    /**
     * 运行状态 1 开启  2 关闭
     */
    private Integer status;
    /**
     * 彩种排序
     */
    private Integer sort;

    //-------------------------------------------------------------------
    @Transient
    private String name;
}
