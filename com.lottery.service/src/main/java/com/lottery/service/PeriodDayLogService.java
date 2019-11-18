package com.lottery.service;

import com.common.mongo.MongoService;
import com.lottery.domain.PeriodDayLog;
import com.lottery.domain.model.LotteryCategoryEnum;

/**
 *
 */
public interface PeriodDayLogService extends MongoService<PeriodDayLog> {
    PeriodDayLog findByCategoryAndDay(LotteryCategoryEnum category,String day);
}
