package com.lottery.main.controller.dto;

import com.common.util.AbstractDTO;
import lombok.Data;

@Data
public class LotteryPeriodDto extends AbstractDTO {
    /**
     * 彩种
     */
    private Integer lotteryType;

    /**
     * 代理商标识
     */
    private String proxyId;

    /**
     * 是否开奖 1 开奖 2否
     */
    private Integer status;

}
