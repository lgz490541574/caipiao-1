package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.PeriodDayLog;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.PeriodDayLogService;
import org.springframework.stereotype.Service;

@Service
public class PeriodDayLogServiceImpl extends AbstractMongoService<PeriodDayLog> implements PeriodDayLogService {
    @Override
    protected Class getEntityClass() {
        return PeriodDayLog.class;
    }


    @Override
    public PeriodDayLog findByCategoryAndDay(LotteryCategoryEnum category, String day) {
        PeriodDayLog query=new PeriodDayLog();
        query.setLotteryCategory(category.getValue());
        query.setDay(day);
        return findByOne(query);
    }



}
