package com.lottery.service;

import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.Config;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.dto.PeriodResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface LotteryPeriodService {

    /**
     * 添加彩期
     *
     * @param period
     */
    void insert(LotteryPeriod period);

    void save(LotteryPeriod period);

    /**
     *
     * @param category
     * @param proxyId
     * @param resultDate
     * @param result
     */
    boolean saveByResultDateTime(LotteryCategoryEnum category,String proxyId,String resultDate,String result);
    /**
     * 生成彩期
     * @param month
     */
    void buildLHCPeriod(String month);

    /**
     * 开奖
     *
     * @param type
     * @param proxyId
     * @param code
     * @param result
     */
    boolean drawPeriod(LotteryCategoryEnum type, String proxyId, String code, String result);


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
     * 查找最后一彩期
     * @param category
     * @param proxyId
     * @return
     */
    LotteryPeriod findLast(LotteryCategoryEnum category,String proxyId);

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
     *
     * @param period
     * @param pageable
     * @return
     */
    Page<LotteryPeriod> queryByPage(LotteryPeriod period, Pageable pageable);


    /**
     * 获取彩期表名
     *
     * @param lotteryType
     * @param proxyId
     * @return
     */
    String getCollectionName(LotteryCategoryEnum lotteryType,  String proxyId);


    /**
     * 计算开奖结果
     * @param lotteryType
     * @param orderList
     * @param configs
     * @return
     */
    List<PeriodResult> caculateResult(LotteryCategoryEnum lotteryType, List<OrderInfo> orderList, List<Config> configs);
}
