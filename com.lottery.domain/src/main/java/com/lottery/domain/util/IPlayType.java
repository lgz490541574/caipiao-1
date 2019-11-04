package com.lottery.domain.util;

/**
 * 玩法接口
 */
public interface IPlayType {
    TicketRule[] getRules();

    IPrizeCheck getPrizeCheck();

    IValidateOrder getOrderCheck();

    IOrderSplit getOrderSplit();

    String getName();
}
