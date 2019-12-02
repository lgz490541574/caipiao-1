package com.lottery.service;

import com.common.mongo.MongoService;
import com.lottery.domain.Config;
import com.lottery.domain.model.LotteryCategoryEnum;

import java.util.Map;

/**
 * 玩法设置
 */
public interface ConfigService extends MongoService<Config> {
  /**
   * 修改配置
   * @param key
   * @param name
   * @param content
   * @param remark
   */
  void upData(String key, String name, String content, String remark);

  /**
   * 查找配置
   * @param key
   * @return
   */
  String findCfg(String key);

  /**
   * 根据代理id查找配置
   * @param key
   * @param proxyId
   * @return
   */
  String findCfg(String key, String proxyId);

    /**
     * 修改或修改玩法配置
     * @param config
     */
  void saveOrUpdate(Config config);

  /**
   * 获取玩法详情
   * @param proxyId
   * @param lottery
   * @param playType
   * @return
   */
  public Map<String, Object> getPlayTypeDetail(String proxyId, LotteryCategoryEnum lottery,String playType);

}
