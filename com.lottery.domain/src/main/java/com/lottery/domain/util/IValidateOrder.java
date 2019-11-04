package com.lottery.domain.util;

public interface IValidateOrder {

    /**
     * 验证下单
     * @param ticketCode
     * @return
     */
    public boolean checkTicketCode(String ticketCode);

    /**
     * 验证下单
     * @param ticketCode
     * @param weiShu
     * @return
     */
    public boolean checkTicketCode(String ticketCode,String weiShu);
}
