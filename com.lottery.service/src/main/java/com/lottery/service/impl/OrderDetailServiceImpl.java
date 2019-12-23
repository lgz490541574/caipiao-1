package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.lottery.domain.OrderDetail;
import com.lottery.service.OrderDetailService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends AbstractMongoService<OrderDetail> implements OrderDetailService {
    @Override
    protected Class getEntityClass() {
        return OrderDetail.class;
    }

    @Override
    public List<OrderDetail> queryByOrderInfoId(String orderId) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderInfoId(orderId);
        return queryByPage(orderDetail, PageRequest.of(0, 200)).getContent();
    }
}
