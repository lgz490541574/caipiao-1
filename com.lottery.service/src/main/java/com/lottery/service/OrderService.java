package com.lottery.service;

import com.common.mongo.MongoService;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.model.LotteryCategoryEnum;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService extends MongoService<OrderInfo> {
    /**
     * 下单
     *
     * @param proxyId   代理商
     * @param pin       用户
     * @param type      类型
     * @param playType  玩法
     * @param periodId  期号
     * @param codes     下单号码
     * @param times     部投
     * @param chaseMark 追号标记
     * @param prizeStop 中奖即停
     */
    BigDecimal createOrder(String proxyId, String pin, LotteryCategoryEnum type, String playType, String periodId, List<String[]> codes, Integer times, String chaseMark, YesOrNoEnum prizeStop);

}
