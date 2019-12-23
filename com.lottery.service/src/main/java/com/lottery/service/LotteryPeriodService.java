package com.lottery.service;

import com.lottery.domain.Config;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.PeriodConfig;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.dto.PeriodResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

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
     * @param type
     * @param proxyId
     * @param periodId
     * @param result 开奖号码
     */
    PeriodResult doSettle(LotteryCategoryEnum type, String proxyId, String periodId, String result, Map<String, JSONArray> configArray);

    /**
     * 派彩
     * @param type
     * @param proxyId
     * @param periodId
     */
    void dispatchPeriod(LotteryCategoryEnum type, String proxyId, String periodId);

    /**
     * 查找最后一彩期
     * @param category
     * @param proxyId
     * @return
     */
    LotteryPeriod findLast(LotteryCategoryEnum category,String proxyId);


    /**
     * 查询最后一期未开奖彩期
     * @param category
     * @param proxyId
     * @return
     */
    LotteryPeriod findLastNotOpenResult(LotteryCategoryEnum category, String proxyId);

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
    List<PeriodResult> caculateResult(LotteryCategoryEnum lotteryType, List<OrderInfo> orderList, Map<String, JSONArray> configs);

    /**
     * 开奖
     * @param periodConfig
     * @param countList
     * @param category
     * @return
     */
    PeriodResult getBestRatePeriodCode(PeriodConfig periodConfig, List<PeriodResult> countList, LotteryCategoryEnum category);
}
