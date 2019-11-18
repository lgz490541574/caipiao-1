package com.lottery.service.impl;

import com.common.mongo.AbstractMongoService;
import com.common.util.RPCResult;
import com.lottery.domain.Config;
import com.lottery.service.*;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public void upData(String key, String name, String content, String remark) {
        Config query = new Config();
        query.setKey(key);
        query = findByOne(query);
        if (query == null) {
            query = new Config();
            query.setKey(key);
            query.setName(name);
            query.setContent(content);
            query.setRemark(remark);
        } else {
            query.setKey(key);
            query.setName(name);
            query.setContent(content);
            query.setRemark(remark);
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
        if(config.getId()==null){
            RPCResult<List<ProxyDto>> listRPCResult = proxyRpcService.queryAll();
            for(ProxyDto dto : listRPCResult.getData()){
                String name = dto.getName();
                Config cfg = new Config();
                cfg.setProxyId(dto.getId());
                cfg.setGameType(config.getGameType());
                cfg.setKey(config.getKey());
                if(findByOne(cfg)!=null){
                    continue;
                }
                cfg.setContent(config.getContent());
                if(config.getKey().contains("DSH")){
                    String cfgName = config.getName().replaceAll("九州",name);
                    cfg.setName(cfgName);
                }else {
                    cfg.setName(config.getName());
                }
                cfg.setRemark(config.getRemark());
                cfg.setCodeLimit(config.getCodeLimit());
                save(cfg);
            }
        }else {
            up(config);
        }
    }

    @Override
    protected Class getEntityClass() {
        return Config.class;
    }

}
