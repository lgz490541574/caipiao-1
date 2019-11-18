package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.PeriodConfig;
import com.lottery.service.PeriodConfigService;
import org.springframework.stereotype.Service;

@Service
public class PeriodConfigServiceImpl extends AbstractMongoService<PeriodConfig> implements PeriodConfigService {
    @Override
    protected Class getEntityClass() {
        return PeriodConfig.class;
    }
}
