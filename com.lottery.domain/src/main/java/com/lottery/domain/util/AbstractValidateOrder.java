package com.lottery.domain.util;


public  class AbstractValidateOrder implements IValidateOrder {
    /**
     * 验证下单
     * @param ticketCode
     * @return
     */
    @Override
    public boolean checkTicketCode(String ticketCode) {
        throw new RuntimeException("not instance");
    }

    /**
     * 验证下单
     * @param ticketCode
     * @param weiShu
     * @return
     */
    @Override
    public boolean checkTicketCode(String ticketCode, String weiShu) {
        throw new RuntimeException("not instance");
    }
}
