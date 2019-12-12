package com.lottery.service.impl;

import com.common.exception.ApplicationException;
import com.common.mongo.AbstractMongoService;
import com.common.util.*;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.*;
import com.lottery.domain.model.ProfitEnum;
import com.lottery.domain.util.IPlayType;
import com.lottery.domain.util.OrderSplitTools;
import com.lottery.service.*;
import com.lottery.service.dto.PeriodResult;
import com.lottery.service.util.IResultBuilder;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.CodeEnum;
import com.mongodb.client.result.UpdateResult;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
public class LotteryPeriodServiceImpl extends AbstractMongoService implements LotteryPeriodService {

    private Map<LotteryCategoryEnum, IResultBuilder> resultBuilderMap = new HashMap<>();
    private Random r = new Random();

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

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private PeriodConfigService periodConfigService;

    @Resource
    private ConfigService configService;

    @Reference
    private ProxyInfoRPCService proxyInfoRPCService;

    @Override
    public void buildLHCPeriod(String money) {
        lhcPerodCreateUtil.build(money);
    }


    /**
     * 获取盈利比例map数据
     *
     * @param periodConfig
     * @return
     */
    public static Map<String, BigDecimal> countProfitAndLoss(PeriodConfig periodConfig) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        Map<String, BigDecimal> map = new HashMap<>();
        try {
            Number minProfit = nf.parse(periodConfig.getMinProfit());
            Number maxProfit = nf.parse(periodConfig.getMaxProfit());
            BigDecimal profit = BigDecimal.valueOf(maxProfit.doubleValue() + minProfit.doubleValue()).divide(BigDecimal.valueOf(2));
            Number minLoss = nf.parse(periodConfig.getMinLoss());
            Number maxLoss = nf.parse(periodConfig.getMaxLoss());
            BigDecimal loss = BigDecimal.valueOf(minLoss.doubleValue() + maxLoss.doubleValue()).divide(BigDecimal.valueOf(2));
            map.put("profit", profit);
            map.put("loss", BigDecimal.ZERO.subtract(loss));
            map.put("maxProfit", BigDecimal.valueOf(maxProfit.doubleValue()));
            map.put("minProfit", BigDecimal.valueOf(minProfit.doubleValue()));
            map.put("maxLoss", BigDecimal.ZERO.subtract(BigDecimal.valueOf(maxLoss.doubleValue())));
            map.put("minLoss", BigDecimal.ZERO.subtract(BigDecimal.valueOf(minLoss.doubleValue())));
            BigDecimal maxLossMoney = periodConfig.getMaxLossMoney();
            if (maxLossMoney == null) {
                maxLossMoney = BigDecimal.valueOf(5000);
            }
            map.put("maxLossMoney", BigDecimal.ZERO.subtract(maxLossMoney));
        } catch (ParseException e) {
            log.error("类型转换错误", e);
        }
        return map;
    }

    /**
     * 获取最接近盈亏比例的期号开奖结果
     *
     * @param periodConfig
     * @param countList
     * @return
     */
    public String getBestRatePeriodCode(PeriodConfig periodConfig, List<PeriodResult> countList, LotteryCategoryEnum category) {
        String result = null;
        Map<String, BigDecimal> decimalMap = countProfitAndLoss(periodConfig);
        //为正数 最大亏损金额 比如-5000
        BigDecimal maxLossMoney = decimalMap.get("maxLossMoney");
        //最小盈利比 小数  比如0.2
        BigDecimal minProfit = decimalMap.get("minProfit");
        //最大盈利比 小数  比如0.5
        BigDecimal maxProfit = decimalMap.get("maxProfit");
        //最小亏损比 小数  比如-0.2
        BigDecimal minLoss = decimalMap.get("minLoss");
        //最大亏损比 小数  比如-0.5
        BigDecimal maxLoss = decimalMap.get("maxLoss");
        //输赢模式 1 盈利 2 亏损 3 随机
        int status;
        //随机模式则随机输赢模式
        if (periodConfig.getStatus().intValue() == ProfitEnum.RANDOM.getValue()) {
            status = r.nextInt(100) + 1;
        } else {
            status = periodConfig.getStatus().intValue();
        }
        //盈利数据
        List<PeriodResult> profitList = new ArrayList<>();
        //配置内的亏损数据
        List<PeriodResult> lossList = new ArrayList<>();
        //配置外的亏损数据 小于最大亏损金额
        List<PeriodResult> maxLossList = new ArrayList<>();
        //超出最大盈利的数据
        List<PeriodResult> maxList = new ArrayList<>();
        //大于最大亏损金额的数据
        List<PeriodResult> gateMaxLoss = new ArrayList<>();
        for (PeriodResult periodResult : countList) {
            BigDecimal profileMoney = periodResult.getProfileMoney();
            BigDecimal itemRate = periodResult.getRate();
            //盈利模式
            if (itemRate.compareTo(BigDecimal.ZERO) > 0
                    && itemRate.compareTo(maxProfit) <= 0) {
                profitList.add(periodResult);
            }
            //亏损模式
            if (itemRate.compareTo(BigDecimal.ZERO) <= 0
                    && itemRate.compareTo(maxLoss) >= 0 && profileMoney.compareTo(maxLossMoney) >= 0) {
                lossList.add(periodResult);
            }
            //配置外的亏损模式
            if (itemRate.compareTo(BigDecimal.valueOf(-2)) >= 0 && itemRate.compareTo(maxLoss) < 0 && profileMoney.compareTo(maxLossMoney) >= 0) {
                maxLossList.add(periodResult);
            }
            //超出最大盈利数据
            if (itemRate.compareTo(maxProfit) > 0) {
                maxList.add(periodResult);
            }
            //大于最大亏损金额的数据
            if (itemRate.compareTo(BigDecimal.valueOf(-62)) >= 0 && profileMoney.compareTo(maxLossMoney) >= 0) {
                gateMaxLoss.add(periodResult);
            }
        }
//        if (status == ProfitEnum.PROFIT.getValue()) {
//            log.warn(category.name() + " 盈亏状态：" + ProfitEnum.PROFIT.getName());
//            result = getPeriodResult(profitList, lossList, maxList, maxLossList, gameType, gateMaxLoss, countList, periodConfig.getStatus());
//        }
//        if (status == ProfitEnum.LOSS.getValue()) {
//            log.warn(gameType.name() + " 盈亏状态：" + ProfitEnum.LOSS.getName());
//            result = getPeriodResult(lossList, profitList, maxLossList, maxList, gameType, gateMaxLoss, countList, periodConfig.getStatus());
//        }
        return result;
    }

    /**
     * 生成号码并预算
     *
     * @param lotteryType
     * @param orderList
     * @param configs
     * @return
     */
    public List<PeriodResult> caculateResult(LotteryCategoryEnum lotteryType, List<OrderInfo> orderList, List<Config> configs) {
        List<PeriodResult> resultCaculate = new ArrayList<>();

        JSONObject typeConfig = new JSONObject();
        for (Config config : configs) {
            typeConfig.put(config.getKey(), JSONArray.fromObject(config.getContent()));
        }

        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            resultList.add(resultBuilderMap.get(lotteryType).buildResult());
        }
        for (String result : resultList) {
            BigDecimal totalPrizeMoney = BigDecimal.ZERO;
            BigDecimal totalOrderMoney = BigDecimal.ZERO;

            for (OrderInfo info : orderList) {
                IPlayType play = OrderSplitTools.getPlay(lotteryType, info.getPlayType());
                List<TicketInfo> tickets = play.getOrderSplit().doSplit(lotteryType, info.getPlayType());
                JSONArray jsonObject = typeConfig.getJSONArray(info.getPlayType());
                for (TicketInfo ticketItem : tickets) {
                    if (checkPrize(ticketItem, result, play)) {
                        double rate = buildRate(jsonObject, info.getPlayType(), ticketItem);
                        totalPrizeMoney = totalPrizeMoney.add(BigDecimal.valueOf(rate).multiply(BigDecimal.valueOf(ticketItem.getTimes())));
                    }
                }
                totalOrderMoney = totalOrderMoney.add(info.getOrderMoney());
            }
            BigDecimal profileMoney = totalOrderMoney.subtract(totalPrizeMoney);
            BigDecimal rate = profileMoney.divide(totalOrderMoney, 2, BigDecimal.ROUND_HALF_DOWN);
            PeriodResult resultItem = new PeriodResult();
            resultItem.setOrderMoney(totalOrderMoney);
            resultItem.setPrizeMoney(totalPrizeMoney);
            resultItem.setProfileMoney(profileMoney);
            resultItem.setRate(rate);
            resultCaculate.add(resultItem);
        }
        return resultCaculate;
    }

    /**
     * 判断号码是否中奖
     *
     * @param ticketInfo
     * @param issueResult
     * @param playType
     * @return
     */
    public static boolean checkPrize(TicketInfo ticketInfo, String issueResult, IPlayType playType) {
        if (ticketInfo.getWeiShu() != null) {
            return playType.getPrizeCheck().isPrize(issueResult, ticketInfo.getTicketCode(), ticketInfo.getWeiShu());
        } else {
            return playType.getPrizeCheck().isPrize(issueResult, ticketInfo.getTicketCode());
        }

    }

    /**
     * 获取玩法赔率
     *
     * @param jsonObject
     * @param type
     * @param item
     * @return
     */
    public double buildRate(JSONArray jsonObject, String type, TicketInfo item) {
        double rate = 0;
        if (jsonObject.size() == 1) {
            //所有号码赔率
            rate = JSONObject.fromObject(jsonObject.get(0)).getDouble("rate");
        } else {
            //特殊賠率
            if (StringUtils.isNotBlank(type)) {
                Integer index = Integer.parseInt(type) - 2;
                rate = JSONObject.fromObject(jsonObject.get(index)).getDouble("rate");
            } else {
                //单值赔率
                for (int m = 0; m < jsonObject.size(); m++) {
                    JSONObject obj = JSONObject.fromObject(jsonObject.get(m));
                    if (item.getTicketCode().equals(obj.getString("code"))) {
                        rate = obj.getDouble("rate");
                        break;
                    }
                }
            }
        }
        return rate;
    }

    @PostConstruct
    public void init() {
        this.buildPropertyDescriptor();
        LotteryCategoryEnum[] types = LotteryCategoryEnum.values();
        List<ProxyDto> listRPCResult = proxyInfoRPCService.queryAll().getData();
        for (LotteryCategoryEnum item : types) {
            if (item.getParent() != null) {
                if (item.getPrivateLottery() == YesOrNoEnum.YES) {
                    for (ProxyDto dto : listRPCResult) {
                        buildIndex(getCollectionName(item, dto.getId()));
                    }
                } else {
                    buildIndex(getCollectionName(item, null));
                }
            }
        }
        buildIndex(primaryTemplate.getCollectionName(getEntityClass()));
        //快三生成结果
        resultBuilderMap.put(LotteryCategoryEnum.KS, () -> {
            List<String> ksCodes = Arrays.asList(CodeEnum.KS_CODE_NUMBER.getCodes());
            return buildPeriodResult(ksCodes, 3, false, true);
        });
        //时时彩生成结果
        resultBuilderMap.put(LotteryCategoryEnum.SSC, () -> {
            List<String> sscCodes = Arrays.asList(CodeEnum.SSC_CODE_NUMBER.getCodes());
            //彩票号码个数
            int size = 5;
            //号码重复
            boolean repeat = false;
            //号码是否排序
            boolean sort = false;
            return buildPeriodResult(sscCodes, size, repeat, sort);
        });
        //pk10生成结果
        resultBuilderMap.put(LotteryCategoryEnum.PK10, () -> {
            List<String> codes = Arrays.asList(CodeEnum.PK10_CODE_NUMBER.getCodes());
            //彩票号码个数
            int size = 10;
            //号码重复
            boolean repeat = true;
            //号码是否排序
            boolean sort = false;
            return buildPeriodResult(codes, size, repeat, sort);
        });
        //六合彩生成结果
        resultBuilderMap.put(LotteryCategoryEnum.LHC, () ->
        {
            List<String> codes = Arrays.asList(CodeEnum.LHC_CODE_NUMBER.getCodes());
            //彩票号码个数
            int size = 7;
            //号码重复
            boolean repeat = true;
            //号码是否排序
            boolean sort = false;
            return buildPeriodResult(codes, size, repeat, sort);
        });
    }


    @Override
    public void save(LotteryPeriod entity) {
        if (entity.getId() == null) {
            throw new ApplicationException("LotteryPeriodService.save.error:id");
        }
        if (entity.getLotteryType() == null) {
            throw new ApplicationException("LotteryPeriodService.save.error:lotteryType");
        }
        LotteryCategoryEnum category = GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, entity.getLotteryType());
        String collectionName = getCollectionName(category, entity.getProxyId());

        if (entity == null || entity.getId() == null) {
            throw new ApplicationException("Id不能为空");
        }
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(entity.getId());
        query.addCriteria(criteria);
        entity.setUpdateTime(new Date());
        Update update = addUpdate(entity);
        UpdateResult result = primaryTemplate.updateFirst(query, update, entity.getClass(), collectionName);
        if (result.getModifiedCount() == 1) {
            return;
        }
        throw new ApplicationException("mongodb updata error");
    }

    /**
     * 获取彩期表名
     *
     * @param lotteryType
     * @param proxyId
     * @return
     */
    public String getCollectionName(LotteryCategoryEnum lotteryType, String proxyId) {
        proxyId = String.valueOf(NumberUtils.toInt(proxyId));
        if ("0".equals(proxyId)) {
            return "period_" + lotteryType.name();
        }
        return "period_" + lotteryType.name() + "_" + proxyId;
    }


    protected Class getEntityClass() {
        return LotteryPeriod.class;
    }

    @Override
    public void insert(LotteryPeriod period) {
        primaryTemplate.insert(period, getCollectionName(GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, period.getLotteryType()), period.getProxyId()));
    }

    public boolean drawPeriod(LotteryCategoryEnum type, String proxyId, String code, String result) {
        String[] split = StringUtils.split(result, ",");
        for (String item : split) {
            boolean numeric = StringUtils.isNumeric(item.trim());
            if (!numeric) {
                log.error("LotteryPeriod.result.error lotteryType={},result={},code={}", new Object[]{type.getName(), result, code});
                throw new ApplicationException("LotteryPeriod.result.error");
            }
        }
        Query query = new Query();
        Criteria criteria = Criteria.where("code").is(code).and("openStatus").is(YesOrNoEnum.NO.getValue());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("result", result);
        update.set("openStatus", YesOrNoEnum.YES.getValue());
        UpdateResult updateResult = primaryTemplate.updateMulti(query, update, getEntityClass(), getCollectionName(type, proxyId));
        if (updateResult.getMatchedCount() == 0) {
            return true;
        }
        if (updateResult.getModifiedCount() == 1) {
            return true;
        }
        return false;
    }


    @Override
    public boolean saveByResultDateTime(LotteryCategoryEnum category, String proxyId, String resultDate, String result) {
        String[] split = StringUtils.split(result, ",");
        for (String code : split) {
            boolean numeric = StringUtils.isNumeric(code.trim());
            if (!numeric) {
                log.error("LotteryPeriod.result.error lotteryType={},result={},resultDate={}", new Object[]{category.getName(), resultDate, result});
                throw new ApplicationException("LotteryPeriod.result.error");
            }
        }
        Query query = new Query();
        Criteria criteria = Criteria.where("resultDateStr").is(resultDate).and("openStatus").is(YesOrNoEnum.NO.getValue());
        query.addCriteria(criteria);
        Update update = new Update();
        update.set("result", result);
        update.set("openStatus", YesOrNoEnum.YES.getValue());
        UpdateResult updateResult = primaryTemplate.updateMulti(query, update, getEntityClass(), getCollectionName(category, proxyId));
        if (updateResult.getMatchedCount() == 0) {
            return true;
        }
        if (updateResult.getModifiedCount() != 1) {
            return false;
        }

        return true;
    }

    @Override
    public void doSettle(LotteryCategoryEnum type, String proxyId, String code) {
    }

    @Override
    public LotteryPeriod findLast(LotteryCategoryEnum category, String proxyId) {
        LotteryPeriod period = new LotteryPeriod();
        period.setLotteryType(category.getValue());
        period.setProxyId(proxyId);
        Page<LotteryPeriod> lotteryPeriods = queryByPage(period, PageRequest.of(0, 1));
        if (lotteryPeriods.hasNext()) {
            return lotteryPeriods.getContent().get(0);
        }
        return null;
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
        if (YesOrNoEnum.YES.getValue().intValue() != period.getOpenStatus()) {
            throw new ApplicationException("不在开奖状态");
        }

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setPeriodId(period.getId());


//        orderService.queryByPage()


        //设置结算状态
        Update update = new Update();
        update.set("settleStatus", YesOrNoEnum.YES.getValue());
        primaryTemplate.updateFirst(query, update, getEntityClass(), collectionName);
    }


    @Override
    public Page<LotteryPeriod> queryByPage(LotteryPeriod period, Pageable pageable) {
        LotteryCategoryEnum category = GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, period.getLotteryType());
        String proxyId = period.getProxyId();
        String collectionName = getCollectionName(category, proxyId);
        Query query = new Query();
        MongoTemplate template = secondaryTemplate;
        //派奖状态
        if (period.getDispatchStatus() != null) {
            Criteria criteria = Criteria.where("dispatchStatus").is(period.getDispatchStatus());
            query.addCriteria(criteria);
        }
        //开奖状态
        if (period.getOpenStatus() != null) {
            Criteria criteria = Criteria.where("openStatus").is(period.getOpenStatus());
            query.addCriteria(criteria);
        }
        //结算状态
        if (period.getSettleStatus() != null) {
            Criteria criteria = Criteria.where("settleStatus").is(period.getSettleStatus());
            query.addCriteria(criteria);
        }
        //开奖状态
        if (period.getResultStatus() != null) {
            Criteria criteria = Criteria.where("resultStatus").is(period.getResultStatus());
            query.addCriteria(criteria);
        }
        //彩期
        if (period.getCode() != null) {
            Criteria criteria = Criteria.where("code").is(period.getCode());
            query.addCriteria(criteria);
        }
        long count = template.count(query, LotteryPeriod.class, collectionName);
        Sort sort = buildSort(period);
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        query.with(pageRequest);
        List<LotteryPeriod> list = template.find(query, LotteryPeriod.class, collectionName);
        return new PageImpl<>(list, pageable, count);
    }

    /**
     * 生成开奖结果
     *
     * @param lotteryCodes
     * @param size
     * @param isRepeat
     * @param isSort
     * @return
     */
    private String buildPeriodResult(List<String> lotteryCodes, int size, boolean isRepeat, boolean isSort) {
        StringBuffer resultStr = new StringBuffer();
        for (int j = 0; j < size; j++) {
            String element = lotteryCodes.get(r.nextInt(lotteryCodes.size()));
            //开奖号码不予许重复则要删除该号码
            if (isRepeat) {
                lotteryCodes.remove(element);
            }
            resultStr.append(element + ",");
        }
        String periodCode = resultStr.substring(0, resultStr.length() - 1);
        if (isSort) {
            List<String> periodCodes = new ArrayList<>(Arrays.asList(periodCode.split(",")));
            Collections.sort(periodCodes);
            periodCode = "";
            for (String str : periodCodes) {
                periodCode += str + ",";
            }
            periodCode = periodCode.substring(0, periodCode.length() - 1);
        }
        return periodCode;
    }
}
