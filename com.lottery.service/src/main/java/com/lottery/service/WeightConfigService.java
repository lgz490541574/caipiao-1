package com.lottery.service;

import com.common.mongo.MongoService;
import com.lottery.domain.WeightConfig;

import java.util.List;

public interface WeightConfigService extends MongoService<WeightConfig> {

    /**
     * 添加或修改权重配置
     * @param weightConfig
     */
    void saveOrUpdate(WeightConfig weightConfig);

    /**
     * 查找代理商下的所有权重配置
     * @param proxyId
     * @return
     */
    List<WeightConfig> queryAllByproxyId(String proxyId);

    /**
     * 获取权重
     * @param proxyId
     * @return
     */
    Integer getWeightByOrder(String proxyId);

    /**
     * 构建各个代理商当前随机权重
     */
    void buildRandomWeight();
}
