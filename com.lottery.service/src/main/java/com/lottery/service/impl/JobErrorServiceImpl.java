package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.JobErrorInfo;
import com.lottery.domain.OrderDetail;
import com.lottery.service.JobErrorService;
import com.lottery.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class JobErrorServiceImpl extends AbstractMongoService<JobErrorInfo> implements JobErrorService {
    @Override
    protected Class getEntityClass() {
        return JobErrorInfo.class;
    }
}
