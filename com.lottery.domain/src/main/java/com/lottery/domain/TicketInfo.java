package com.lottery.domain;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public  class TicketInfo implements Serializable {
    /**
     * 下单号码
     */
    private String ticketCode;

    /**
     * 是否中奖
     */
    private Boolean prize;

    /**
     * 位数
     */
    private String weiShu;

    /**
     * 赔率
     */
    private BigDecimal rate;
}
