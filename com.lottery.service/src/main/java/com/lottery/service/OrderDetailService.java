package com.lottery.service;

import com.common.mongo.MongoService;
import com.lottery.domain.OrderDetail;

import java.util.List;

public interface OrderDetailService extends MongoService<OrderDetail> {
    /**
     * 根据订单主表查找订单详情
     * @param orderId
     * @return
     */
    public List<OrderDetail> queryByOrderInfoId(String orderId);
}
