package com.lottery.service;

import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;

public interface LotteryPeriodService {

    /**
     * 添加彩期
     *
     * @param period
     */
    void insert(LotteryPeriod period);

    /**
     * 开奖
     *
     * @param type
     * @param proxyId
     * @param code
     * @param result
     */
    void drawPeriod(LotteryCategoryEnum type, String proxyId, String code, String result);

    /**
     * 结算
     * @param type
     * @param proxyId
     * @param code
     */
    void doSettle(LotteryCategoryEnum type,  String proxyId, String code);

    /**
     * 派彩
     * @param type
     * @param proxyId
     * @param code
     */
    void syncAccount(LotteryCategoryEnum type,  String proxyId, String code);

    /**
     * 查询彩期
     * @param category
     * @param proxyId
     * @return
     */
    LotteryPeriod findById(LotteryCategoryEnum  category,String proxyId,String periodId);

    /**
     * 查询彩期
     * @param category
     * @param proxyId
     * @return
     */
    LotteryPeriod findByCode(LotteryCategoryEnum  category,  String proxyId, String periodId);
}
