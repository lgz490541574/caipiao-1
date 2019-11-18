package com.lottery.service;

import com.common.mongo.MongoService;
import com.lottery.domain.LotteryConfig;
import com.lottery.domain.model.LotteryCategoryEnum;

import java.util.List;

/**
 * 彩种配置接口
 */
public interface LotteryConfigService extends MongoService<LotteryConfig> {


    /**
     * 根据代理商查找所有彩种配置数据
     * @param proxyId
     * @return
     */
    List<LotteryConfig> findProxyAllConfigs(String proxyId);


//    void updateStatusByProxyId(String proxyId, Integer status);

    /**
     * 根据代理商id和彩种类型查找
     * @param proxyId
     * @param type
     * @return
     */
    LotteryConfig findByProxyAndType(String proxyId, Integer type);

}
