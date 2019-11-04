package com.lottery.domain.util;

import com.lottery.domain.TicketInfo;
import com.lottery.domain.model.LotteryCategoryEnum;

import java.util.List;

public interface IOrderSplit {
    List<TicketInfo> doSplit(LotteryCategoryEnum lotteryType, String playType, String ...codes);
}