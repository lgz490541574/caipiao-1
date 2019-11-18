package com.lottery.service.impl;

import com.common.exception.BizException;
import com.common.mongo.AbstractMongoService;
import com.common.util.GlosseryEnumUtils;
import com.common.util.StringUtils;
import com.common.util.model.OrderTypeEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryConfig;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryConfigService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class LotteryConfigServiceImpl extends AbstractMongoService<LotteryConfig> implements LotteryConfigService {

    @Resource
    private RedisTemplate redisTemplate;
    private String system = "lottery.";

    @Override
    public void save(LotteryConfig lotteryConfig) {
        LotteryConfig query = new LotteryConfig();
        query.setProxyId(lotteryConfig.getProxyId());
        query.setLotteryType(lotteryConfig.getLotteryType());
        LotteryConfig one = super.findByOne(query);
        //有数据 则进行数据修改
        if (one != null) {
            LotteryConfig upEntity = new LotteryConfig();
            upEntity.setId(one.getId());
            if (lotteryConfig.getId() != null) {
                LotteryConfig byId = findById(lotteryConfig.getId());
                upEntity.setSort(byId.getSort());
            } else {
                LotteryConfig config = new LotteryConfig();
                config.setProxyId(lotteryConfig.getProxyId());
                config.setOrderColumn("sort");
                config.setOrderType(OrderTypeEnum.DESC);
                Page<LotteryConfig> lotteryConfigs = queryByPage(config, new PageRequest(0, 1));
                if (lotteryConfigs.hasContent()) {
                    LotteryConfig last = lotteryConfigs.getContent().get(0);
                    upEntity.setSort(last.getSort() + 1);
                } else {
                    upEntity.setSort(0);
                }
            }
            up(upEntity);
            return;
        }
        save(lotteryConfig);
        String key = system + lotteryConfig.getProxyId() + ".lottery.configs.all";
        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
        String oneKey = system + lotteryConfig.getProxyId() + ".lottery.config." + lotteryConfig.getLotteryType();
        if (redisTemplate.hasKey(oneKey)) {
            redisTemplate.delete(oneKey);
        }
        String lhcKey = system + lotteryConfig.getProxyId() + "." + lotteryConfig.getLotteryType() + ".period.game.in";
        if (redisTemplate.hasKey(lhcKey)) {
            redisTemplate.delete(lhcKey);
        }
    }


    @Override
    public List<LotteryConfig> findProxyAllConfigs(String proxyId) {
        String key = system + proxyId + ".lottery.configs.all";
        List<LotteryConfig> lotteryConfigs = null;
        if (redisTemplate.hasKey(key)) {
            lotteryConfigs = (List<LotteryConfig>) redisTemplate.opsForValue().get(key);
        } else {
            LotteryConfig config = new LotteryConfig();
            config.setProxyId(proxyId);
            config.setOrderType(OrderTypeEnum.ASC);
            config.setOrderColumn("sort");
            lotteryConfigs = query(config);
            redisTemplate.opsForValue().set(key, lotteryConfigs, 60, TimeUnit.SECONDS);
        }
        return lotteryConfigs;
    }

    @Override
    public LotteryConfig findByProxyAndType(String proxyId, Integer lotteryType) {
        if (proxyId == null) {
            throw new BizException("proxyId.null", "proxyId不能为空");
        }
        if (lotteryType == null) {
            throw new BizException("type.null", "GameTypeEnum不能为空");
        }
        LotteryConfig query = new LotteryConfig();
        query.setProxyId(proxyId);
        query.setLotteryType(lotteryType);
        return findByOne(query);
    }


    @Override
    public Page<LotteryConfig> queryByPage(LotteryConfig entity, Pageable pageable) {
        Page<LotteryConfig> lotteryConfigs = super.queryByPage(entity, pageable);
        if (lotteryConfigs.hasContent()) {
            for (LotteryConfig config : lotteryConfigs) {
                config.setName(GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, config.getLotteryType()).getName());
            }
        }
        return lotteryConfigs;

    }

    @Override
    protected Class getEntityClass() {
        return LotteryConfig.class;
    }
}
