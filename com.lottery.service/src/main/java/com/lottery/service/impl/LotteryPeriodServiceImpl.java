package com.lottery.service.impl;

import com.common.exception.ApplicationException;
import com.common.util.GlosseryEnumUtils;
import com.common.util.StringUtils;
import com.common.util.model.OrderTypeEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.OrderInfo;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.OrderService;
import org.springframework.data.domain.*;
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

    @Resource
    private LHCPerodCreateUtil lhcPerodCreateUtil;

    @Override
    public void buildLHCPeriod(String money) {
        lhcPerodCreateUtil.build(money);
    }

    /**
     * 获取彩期表名
     *
     * @param lotteryType
     * @param proxyId
     * @return
     */
    public String getCollectionName(LotteryCategoryEnum lotteryType, String proxyId) {
        if (StringUtils.isBlank(proxyId)) {
            return "lotteryPeriod" + lotteryType.name();
        }
        return "lotteryPeriod" + lotteryType.name() + "_" + proxyId;
    }


    protected Class getEntityClass() {
        return LotteryPeriod.class;
    }

    @Override
    public void insert(LotteryPeriod period) {
        primaryTemplate.insert(period, getCollectionName(GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, period.getLotteryType()), period.getProxyId()));
    }

    public void drawPeriod(LotteryCategoryEnum type, String proxyId, String code, String result) {
        Query query = new Query();
        Criteria criteria = Criteria.where("proxyId").is(proxyId).and("code").is(code);
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("result", result);
        secondaryTemplate.findAndModify(query, update, getEntityClass(), getCollectionName(type, proxyId));
    }

    @Override
    public void doSettle(LotteryCategoryEnum type, String proxyId, String code) {
    }


    @Override
    public LotteryPeriod findById(LotteryCategoryEnum category, String proxyId, String periodId) {
        String collectionName = getCollectionName(category, proxyId);
        Query query = new Query();
        Criteria criteria = Criteria.where("_id").is(periodId);
        query.addCriteria(criteria);
        return secondaryTemplate.findOne(query, LotteryPeriod.class, collectionName);
    }

    @Override
    public LotteryPeriod findByCode(LotteryCategoryEnum category, String proxyId, String periodCode) {
        String collectionName = getCollectionName(category, proxyId);
        Query query = new Query();
        Criteria criteria = Criteria.where("code").is(periodCode);
        query.addCriteria(criteria);
        return secondaryTemplate.findOne(query, LotteryPeriod.class, collectionName);
    }

    @Override
    public void syncAccount(LotteryCategoryEnum type, String proxyId, String code) {
        Query query = new Query();
        Criteria criteria = Criteria.where("proxyId").is(proxyId).and("code").is(code);
        query.addCriteria(criteria);
        String collectionName = getCollectionName(type, proxyId);
        List<LotteryPeriod> list = secondaryTemplate.find(query, getEntityClass(), collectionName);
        LotteryPeriod period = list.get(0);

        if (StringUtils.isNotBlank(period.getResult())) {
            throw new ApplicationException("未生成开奖结果");
        }
        if (YesOrNoEnum.YES.getValue().intValue() != period.getStatus()) {
            throw new ApplicationException("不在开奖状态");
        }

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPeriodId(period.getId());


//        orderService.queryByPage()


        //设置结算状态
        Update update = new Update();
        update.set("settleStatus", YesOrNoEnum.YES.getValue());
        secondaryTemplate.findAndModify(query, update, getEntityClass(), collectionName);
    }


    @Override
    public Page<LotteryPeriod> queryByPage(LotteryCategoryEnum category,YesOrNoEnum status, String proxyId, Pageable pageable) {
        String collectionName = getCollectionName(category, proxyId);
        Query query = new Query();
        String orderColum = "createTime";
        Sort.Direction sortType = Sort.Direction.DESC;
        MongoTemplate template = secondaryTemplate;
        if(status!=null){
            Criteria criteria = Criteria.where("status").is(status.getValue());
            query.addCriteria(criteria);
        }
        long count = template.count(query, LotteryPeriod.class, collectionName);
        Sort sort = new Sort(sortType, orderColum);
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        query.with(pageRequest);
        List<LotteryPeriod> list = template.find(query, LotteryPeriod.class, collectionName);
        Page<LotteryPeriod> pagelist = new PageImpl<LotteryPeriod>(list, pageable, count);
        return pagelist;
    }

}
