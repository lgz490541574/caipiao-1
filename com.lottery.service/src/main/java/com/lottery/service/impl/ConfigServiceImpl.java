package com.lottery.service.impl;

import com.common.exception.BizException;
import com.common.mongo.AbstractMongoService;
import com.common.util.RPCResult;
import com.common.util.model.OrderTypeEnum;
import com.lottery.domain.Config;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.IPlayType;
import com.lottery.domain.util.TicketRule;
import com.lottery.service.*;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ConfigServiceImpl extends AbstractMongoService<Config> implements ConfigService {
    @Resource
    private BetConfigService betConfigService;
    @Resource
    private PeriodConfigService periodConfigService;
    @Resource
    private CommissionConfigService commissionConfigService;
    @Resource
    private LotteryConfigService lotteryConfigService;
    @Resource
    private WeightConfigService weightConfigService;
    @Reference
    private ProxyInfoRPCService proxyRpcService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 系统类型
     */
    private String system = "lottery.";


    @Override
    public Map<String, Object> getPlayTypeDetail(String proxyId, LotteryCategoryEnum category, String playType) {
        Class playTypeClass = category.getPlayType();
        Enum[] enumConstants = (Enum[]) playTypeClass.getEnumConstants();
        Enum currentType = null;
        for (Enum item : enumConstants) {
            if (item.name().equals(playType)) {
                currentType = item;
            }
        }
        IPlayType playTypes = (IPlayType) currentType;
        TicketRule[] ticketRules = playTypes.getRules();
        return null;
    }

    @Override
    public void upData(String key, String name, String content, String remark) {
        Config query = new Config();
        query.setKey(key);
        query = findByOne(query);
        if (query == null) {
            query = new Config();
            query.setKey(key);
            query.setContent(content);
        } else {
            query.setKey(key);
            query.setContent(content);
        }
        save(query);
    }

    @Override
    public String findCfg(String key) {
        Config query = new Config();
        query.setKey(key);
        query = findByOne(query);
        return query.getContent();
    }

    @Override
    public String findCfg(String key, String proxyId) {
        Config query = new Config();
        query.setKey(key);
        query.setProxyId(proxyId);
        query = findByOne(query);
        return query.getContent();
    }

    @Override
    public void saveOrUpdate(Config config) {
        if (config.getId() != null) {
            Config oldData = findById(config.getId());
            if(!config.getProxyId().equals(oldData.getProxyId())){
                throw new BizException("data.error","非法操作");
            }
            save(config);
        } else {
            insert(config);
        }
    }

    @Override
    public List<Config> queryAll() {
        List<Config> allConfig = new ArrayList<>();
        Config configQuery = new Config();
        configQuery.setOrderColumn("createTime");
        configQuery.setOrderType(OrderTypeEnum.ASC);
        Page<Config> configs = queryByPage(configQuery, PageRequest.of(0, 100));
        while (configs != null && configs.hasNext()) {
            allConfig.addAll(configs.getContent());
            if (configs.hasNext()) {
                configs = queryByPage(configQuery, configs.nextPageable());
            } else {
                configs = null;
            }
        }
        return allConfig;
    }

    @Override
    protected Class getEntityClass() {
        return Config.class;
    }

}
