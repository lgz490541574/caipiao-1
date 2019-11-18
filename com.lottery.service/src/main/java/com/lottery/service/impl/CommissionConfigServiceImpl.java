package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.CommissionConfig;
import com.lottery.service.CommissionConfigService;
import org.springframework.stereotype.Service;

@Service
public class CommissionConfigServiceImpl extends AbstractMongoService<CommissionConfig> implements CommissionConfigService {
    @Override
    protected Class getEntityClass() {
        return CommissionConfig.class;
    }
}
