package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.BetConfig;
import com.lottery.service.BetConfigService;
import org.springframework.stereotype.Service;

@Service
public class BetConfigServiceImpl extends AbstractMongoService<BetConfig> implements BetConfigService {

    @Override
    protected Class getEntityClass() {
        return BetConfig.class;
    }
}
