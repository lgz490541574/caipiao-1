package com.lottery.service.impl;

import com.common.exception.BizException;
import com.common.mongo.AbstractMongoService;
import com.common.util.RPCResult;
import com.common.util.model.OrderTypeEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.WeightConfig;
import com.lottery.service.WeightConfigService;
import com.lottery.service.util.TimeUtils;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBCollection;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WeightConfigServiceImpl extends AbstractMongoService<WeightConfig> implements WeightConfigService {

    private Logger logger = LoggerFactory.getLogger(WeightConfigServiceImpl.class);

    @Resource
    private RedisTemplate redisTemplate;
    private String system = "lottery.";
    @Reference
    private ProxyInfoRPCService proxyRpcService;

    @Override
    public void saveOrUpdate(WeightConfig weightConfig) {
        String key = system + weightConfig.getProxyId() + ".weight.config.all";
        redisTemplate.delete(key);
        save(weightConfig);
    }

    @Override
    public List<WeightConfig> queryAllByproxyId(String proxyId) {
        String key = system + proxyId + ".weight.config.all";
        List<WeightConfig> configs;
        if (redisTemplate.hasKey(key)) {
            configs = (List<WeightConfig>) redisTemplate.opsForValue().get(key);
            return configs;
        }
        WeightConfig query = new WeightConfig();
        query.setProxyId(proxyId);
        query.setStatus(YesOrNoEnum.YES.getValue());
        query.setOrderType(OrderTypeEnum.DESC);
        query.setOrderColumn("rate");
        configs = query(query);
        redisTemplate.opsForValue().set(key, configs, 600, TimeUnit.SECONDS);
        return configs;
    }

    @Override
    public Integer getWeightByOrder(String proxyId) {
        String key = system + proxyId + ".weight.value";
        int weight = 0;
        if (redisTemplate.hasKey(key)) {
            weight = (int) redisTemplate.opsForValue().get(key);
            return weight;
        }
        return weight;
    }

    @Override
    public void buildRandomWeight() {


    }

    @Override
    protected Class getEntityClass() {
        return WeightConfig.class;
    }
}
