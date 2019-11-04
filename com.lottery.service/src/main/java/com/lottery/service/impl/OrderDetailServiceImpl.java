package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.OrderDetail;
import com.lottery.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends AbstractMongoService<OrderDetail> implements OrderDetailService {
    @Override
    protected Class getEntityClass() {
        return OrderDetail.class;
    }
}
