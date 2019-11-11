package com.lottery.service.impl;

import com.common.exception.ApplicationException;
import com.common.util.GlosseryEnumUtils;
import com.common.util.StringUtils;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LotteryPeriodServiceImpl implements LotteryPeriodService {


    @Resource
    private OrderService orderService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource(name = "primary")
    protected MongoTemplate primaryTemplate;

    @Resource(name = "secondary")
    protected MongoTemplate secondaryTemplate;



    /**
     * 获取彩期表名
     * @param lotteryType
     * @param privateLottery
     * @return
     */
    private String getCollectionName(LotteryCategoryEnum lotteryType,YesOrNoEnum privateLottery,String proxyId) {
        return "lotteryPeriod" + lotteryType.name()+(privateLottery==YesOrNoEnum.NO?"":"_"+proxyId);
    }


    protected Class getEntityClass() {
        return LotteryPeriod.class;
    }

    @Override
    public void insert(LotteryPeriod period) {
        primaryTemplate.insert(period, getCollectionName(GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, period.getLotteryType()),GlosseryEnumUtils.getItem(YesOrNoEnum.class,period.getPrivateLottery()),period.getProxyId()));
    }

    public void drawPeriod(LotteryCategoryEnum type,  String proxyId, String code, String result) {
        Query query = new Query();
        Criteria criteria = Criteria.where("proxyId").is(proxyId).and("code").is(code);
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("result", result);
        secondaryTemplate.findAndModify(query, update, getEntityClass(), getCollectionName(type,type.getPrivateLottery(),proxyId));
    }

    @Override
    public void doSettle(LotteryCategoryEnum type,  String proxyId, String code) {
    }


    @Override
    public LotteryPeriod findById(LotteryCategoryEnum category, String proxyId,String periodId) {
        String collectionName = getCollectionName(category, category.getPrivateLottery(), proxyId);
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(periodId);
        query.addCriteria(criteria);
        return secondaryTemplate.findOne(query,LotteryPeriod.class,collectionName);
    }

    @Override
    public LotteryPeriod findByCode(LotteryCategoryEnum category,  String proxyId, String code) {
        String collectionName = getCollectionName(category, category.getPrivateLottery(), proxyId);
        Query query = new Query();
        Criteria criteria = Criteria.where("code").is(code);
        query.addCriteria(criteria);
        return secondaryTemplate.findOne(query,LotteryPeriod.class,collectionName);
    }

    @Override
    public void syncAccount(LotteryCategoryEnum type, String proxyId, String code) {
        Query query = new Query();
        Criteria criteria = Criteria.where("proxyId").is(proxyId).and("code").is(code);
        query.addCriteria(criteria);
        String collectionName = getCollectionName(type, type.getPrivateLottery(), proxyId);
        List<LotteryPeriod> list = secondaryTemplate.find(query, getEntityClass(), collectionName);
        LotteryPeriod period = list.get(0);

        if(StringUtils.isNotBlank(period.getResult())){
            throw new ApplicationException("未生成开奖结果");
        }
        if(YesOrNoEnum.YES.getValue().intValue()!=period.getStatus()){
            throw new ApplicationException("不在开奖状态");
        }

        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setPeriodId(period.getId());



//        orderService.queryByPage()




        //设置结算状态
        Update update = new Update();
        update.set("settleStatus", YesOrNoEnum.YES.getValue());
        secondaryTemplate.findAndModify(query, update, getEntityClass(), collectionName);
    }


}
