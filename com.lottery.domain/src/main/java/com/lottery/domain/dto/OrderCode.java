package com.lottery.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCode implements Serializable {
    /**
     * 彩种玩法
     */
    private String PlayType;
    /**
     * 下单号码
     */
    private String lotteryCode;

}
