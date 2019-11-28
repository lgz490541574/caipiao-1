package com.lottery.service;

import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LotteryPeriodService {

    /**
     * 添加彩期
     *
     * @param period
     */
    void insert(LotteryPeriod period);

    /**
     * 生成彩期
     * @param money
     */
    void buildLHCPeriod(String money);

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
     *
     * @param type
     * @param proxyId
     * @param code
     */
    void doSettle(LotteryCategoryEnum type, String proxyId, String code);

    /**
     * 派彩
     *
     * @param type
     * @param proxyId
     * @param code
     */
    void syncAccount(LotteryCategoryEnum type, String proxyId, String code);

    /**
     * 查询彩期
     *
     * @param category
     * @param proxyId
     * @return
     */
    LotteryPeriod findById(LotteryCategoryEnum category, String proxyId, String periodId);

    /**
     * 查询彩期
     *
     * @param category
     * @param proxyId
     * @param periodCode
     * @return
     */
    LotteryPeriod findByCode(LotteryCategoryEnum category, String proxyId, String periodCode);


    /**
     * 查询分页
     *
     * @param category
     * @param status
     * @param proxyId
     * @return
     */
    Page<LotteryPeriod> queryByPage(LotteryCategoryEnum category,YesOrNoEnum status, String proxyId, Pageable pageable);


    /**
     * 获取彩期表名
     *
     * @param lotteryType
     * @param proxyId
     * @return
     */
    String getCollectionName(LotteryCategoryEnum lotteryType,  String proxyId);
}
