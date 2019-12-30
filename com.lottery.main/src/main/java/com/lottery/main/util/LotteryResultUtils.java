package com.lottery.main.util;

import com.common.exception.ApplicationException;
import com.common.httpclient.HttpClientUtil;
import com.common.util.DateUtil;
import com.common.util.StringUtils;
import com.common.util.model.OrderTypeEnum;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryPeriodService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LotteryResultUtils {

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    private HttpClientUtil httpClientUtil = new HttpClientUtil();

    Map<LotteryCategoryEnum, ILotteryResultService> serviceResult = new HashMap();
    Map<LotteryCategoryEnum, String> resultUrlMap = new HashMap<>();
    Map<LotteryCategoryEnum, String> resultListMap = new HashMap<>();

    public static void main(String[] args) {

    }

    private String lotteryOpenKey = "lottery.period.open.{0}.type.{1}.proxy.{2}";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired(required = false)
    private RocketMQTemplate rocketMQTemplate;

    public void pushResultToMq(LotteryCategoryEnum category, String proxyId, String code, String result) {
        if (StringUtils.isBlank(proxyId)) {
            proxyId = "";
        }
        JSONObject item = new JSONObject();
        item.put("type", category.getValue());
        item.put("proxyId", proxyId);
        item.put("code", code);
        item.put("result", result);
        rocketMQTemplate.convertAndSend("lottery-open-topic", item.toString());
    }

    private void doSyncData(LotteryCategoryEnum category, String code) {
        LotteryPeriod period = findLottery(category);
        if (period == null) {
            return;
        }
        if (StringUtils.isBlank(period.getResultDateStr())) {
            LotteryPeriod upEntity = new LotteryPeriod();
            upEntity.setId(period.getId());
            upEntity.setLotteryType(category.getValue());
            upEntity.setResultDateStr(DateUtil.formatDateTime(period.getResultDate(), "yyyy-MM-dd HH:mm:ss"));
            lotteryPeriodService.save(upEntity);
            period.setResultDateStr(upEntity.getResultDateStr());
        }

        //如果不是六合彩则拉取详情
        if (category != LotteryCategoryEnum.LHC_XG) {
            DateTime dateTime = new DateTime(period.getResultDate()).plusMinutes(category.getRule().getLongTime());
            if (dateTime.toDate().before(DateTime.now().toDate())) {
                DateTime day = new DateTime(period.getResultDate());
                String date = "";
                if (day.plusDays(1).getDayOfYear() == DateTime.now().getDayOfYear()) {
                    date = DateUtil.formatDateTime(day.toDate(), "yyyy-MM-dd");
                } else {
                    date = DateUtil.formatDateTime(new Date(), "yyyy-MM-dd");
                }
                drawPeriodList(category, code, date);
                return;
            }
        }
        DateTime time = DateTime.now();
        time = time.plusSeconds(1);
        if (!period.getResultDate().before(time.toDate())) {
            return;
        }
        drawPeriod(category, code, period);
    }

    @PostConstruct
    public void init() {
        //重庆时时彩
        resultUrlMap.put(LotteryCategoryEnum.SSC_CQ, "https://api.api861861.com/CQShiCai/getBaseCQShiCai.do?lotCode=10060");
        resultListMap.put(LotteryCategoryEnum.SSC_CQ, "https://api.api861861.com/CQShiCai/getBaseCQShiCaiList.do?date={0}&lotCode=10060");

        //北京PK10
        resultUrlMap.put(LotteryCategoryEnum.PK10_BJ, "https://api.api861861.com/pks/getLotteryPksInfo.do?lotCode=10001");
        resultListMap.put(LotteryCategoryEnum.PK10_BJ, "https://api.api861861.com/pks/getPksHistoryList.do?date={0}&lotCode=10001");

        //香港六合彩
        resultUrlMap.put(LotteryCategoryEnum.LHC_XG, "https://1680660.com/smallSix/findSmallSixInfo.do?lotCode=10048");


        //广东11选五
        resultUrlMap.put(LotteryCategoryEnum.SYX5_GD, "https://api.api861861.com/ElevenFive/getElevenFiveInfo.do?lotCode=10006");
        resultListMap.put(LotteryCategoryEnum.SYX5_GD, "https://api.api861861.com/ElevenFive/getElevenFiveList.do?date={0}&&lotCode=10006");


        //江西11选五
        resultUrlMap.put(LotteryCategoryEnum.SYX5_JX, "https://api.api861861.com/ElevenFive/getElevenFiveInfo.do?lotCode=10015");
        resultListMap.put(LotteryCategoryEnum.SYX5_JX, "https://api.api861861.com/ElevenFive/getElevenFiveList.do?date={0}&lotCode=10015");

        //安徽11选五
        resultUrlMap.put(LotteryCategoryEnum.SYX5_AH, "https://api.api861861.com/ElevenFive/getElevenFiveInfo.do?lotCode=10017");
        resultListMap.put(LotteryCategoryEnum.SYX5_AH, "https://api.api861861.com/ElevenFive/getElevenFiveList.do?date={0}&lotCode=10017");


        //安徽快三
        resultUrlMap.put(LotteryCategoryEnum.KS_AH, "https://api.api861861.com/lotteryJSFastThree/getBaseJSFastThree.do?lotCode=10030");
        resultListMap.put(LotteryCategoryEnum.KS_AH, "https://api.api861861.com/lotteryJSFastThree/getJSFastThreeList.do?date={0}&lotCode=10030");

        //上海11选五
        resultUrlMap.put(LotteryCategoryEnum.SYX5_SH, "https://api.api861861.com/ElevenFive/getElevenFiveInfo.do?lotCode=10018");
        resultListMap.put(LotteryCategoryEnum.SYX5_SH, "https://api.api861861.com/ElevenFive/getElevenFiveList.do?date={0}&lotCode=10018");

        //天津时时彩
        resultUrlMap.put(LotteryCategoryEnum.SSC_TJ, "https://api.api861861.com/CQShiCai/getBaseCQShiCai.do?lotCode=10003");
        resultListMap.put(LotteryCategoryEnum.SSC_TJ, "https://api.api861861.com/CQShiCai/getBaseCQShiCaiList.do?date={0}&lotCode=10003");

        //PC蛋蛋幸运28
        resultUrlMap.put(LotteryCategoryEnum.XY28_GW, "https://api.api861861.com/LuckTwenty/getPcLucky28.do?&lotCode=10046");
        resultListMap.put(LotteryCategoryEnum.XY28_GW, "https://api.api861861.com/LuckTwenty/getPcLucky28List.do?date={0}&lotCode=10046");

        //新疆时时彩
        resultUrlMap.put(LotteryCategoryEnum.SSC_XJ, "https://api.api861861.com/CQShiCai/getBaseCQShiCai.do?lotCode=10004");
        resultListMap.put(LotteryCategoryEnum.SSC_XJ, "https://api.api861861.com/CQShiCai/getBaseCQShiCaiList.do?date={0}&lotCode=10004");

        //幸运飞艇
        resultUrlMap.put(LotteryCategoryEnum.PK10_XYFT, "https://api.api861861.com/pks/getLotteryPksInfo.do?lotCode=10057");
        resultListMap.put(LotteryCategoryEnum.PK10_XYFT, "https://api.api861861.com/pks/getPksHistoryList.do?date={0}&lotCode=10057");


        //北京快三
        serviceResult.put(LotteryCategoryEnum.KS_BJ, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_BJ, "10033");
            }
        });

        //江苏快三
        serviceResult.put(LotteryCategoryEnum.KS_JS, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_JS, "10007");
            }
        });
        //上海快三
        serviceResult.put(LotteryCategoryEnum.KS_SH, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_SH, "10061");
            }
        });
        //安徽快三
        serviceResult.put(LotteryCategoryEnum.KS_AH, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_AH, "10030");
            }
        });
        //贵州快三
        serviceResult.put(LotteryCategoryEnum.KS_GZ, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_GZ, "10062");
            }
        });
        //广西快三
        serviceResult.put(LotteryCategoryEnum.KS_GX, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_GX, "10026");
            }
        });
        //河北快三
        serviceResult.put(LotteryCategoryEnum.KS_HE, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_HE, "10028");
            }
        });
        //吉林快三
        serviceResult.put(LotteryCategoryEnum.KS_JL, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_JL, "10027");
            }
        });
        //湖北快三
        serviceResult.put(LotteryCategoryEnum.KS_HB, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.KS_HB, "10032");
            }
        });

        //新疆时时彩
        serviceResult.put(LotteryCategoryEnum.SSC_XJ, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SSC_XJ, "10004");
            }
        });
        //重庆时时彩
        serviceResult.put(LotteryCategoryEnum.SSC_CQ, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SSC_CQ, "10060");
            }
        });
        //天津时时彩
        serviceResult.put(LotteryCategoryEnum.SSC_TJ, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SSC_TJ, "10003");
            }
        });

        //北京PK10
        serviceResult.put(LotteryCategoryEnum.PK10_BJ, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.PK10_BJ, "10001");
            }
        });
        //幸运飞艇
        serviceResult.put(LotteryCategoryEnum.PK10_XYFT,new ILotteryResultService(){
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.PK10_XYFT, "10057");
            }
        });
        //香港六合彩
        serviceResult.put(LotteryCategoryEnum.LHC_XG, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.LHC_XG, "10048");
            }
        });

        //PC蛋蛋幸运28
        serviceResult.put(LotteryCategoryEnum.XY28_GW, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.XY28_GW, "10046");
            }
        });

        //上海11选五
        serviceResult.put(LotteryCategoryEnum.SYX5_SH, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SYX5_SH, "10018");
            }
        });

        //广东11选五
        serviceResult.put(LotteryCategoryEnum.SYX5_GD, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SYX5_GD, "10006");
            }
        });

        //安徽11选五
        serviceResult.put(LotteryCategoryEnum.SYX5_AH, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SYX5_AH, "10017");
            }
        });

        //江西11选五
        serviceResult.put(LotteryCategoryEnum.SYX5_JX, new ILotteryResultService() {
            @Override
            public void syncLotteryResult() {
                doSyncData(LotteryCategoryEnum.SYX5_JX, "10015");
            }
        });

    }


    public ILotteryResultService findResultService(LotteryCategoryEnum category) {
        return serviceResult.get(category);
    }

    private void drawPeriod(LotteryCategoryEnum category, String lotteryCode, LotteryPeriod period) {
        String url = resultUrlMap.get(category);
        String result = null;
        if (StringUtils.isNotBlank(url)) {
            result = httpClientUtil.sendHttpGet(url);
        } else {
            result = httpClientUtil.sendHttpGet("https://api.api861861.com/lotteryJSFastThree/getBaseJSFastThree.do?lotCode=" + lotteryCode);
        }
        try {
            if (!result.startsWith("{")) {
                log.error("彩种:" + category.getName() + " 彩集失败");
                return;
            }
            JSONObject jsonObject = JSONObject.fromObject(result);
            if (jsonObject.getInt("errorCode") != 0) {
                throw new ApplicationException(category.getName() + ":获取开奖信息失败");
            }
            jsonObject = jsonObject.getJSONObject("result");
            if (!jsonObject.has("data")) {
                log.error("获取开奖数据失败:" + category.getName());
                return;
            }
            if (!jsonObject.has("data")) {
                log.error("获取开奖数据失败:" + category.getName() + " data:" + jsonObject.toString());
                return;
            }
            jsonObject = jsonObject.getJSONObject("data");
            if (jsonObject.size() == 0) {
                return;
            }
            String resultDate = jsonObject.getString("preDrawTime");
            String resultCode = jsonObject.getString("preDrawCode");
            if (period.getResultDateStr().equalsIgnoreCase(resultDate)) {
                LotteryPeriod upEntity = new LotteryPeriod();
                upEntity.setId(period.getId());
                upEntity.setOpenStatus(YesOrNoEnum.YES.getValue());
                upEntity.setLotteryType(category.getValue());
                upEntity.setResult(resultCode);
                pushResultToMq(category, period.getProxyId(), period.getCode(), resultCode);
                lotteryPeriodService.save(upEntity);
                return;
            }

            boolean b = lotteryPeriodService.saveByResultDateTime(category, null, resultDate, resultCode);
            if (!b) {
                String issure = jsonObject.getString("preDrawIssue");
                if (!issure.startsWith("20")) {
                    issure = "20" + issure;
                }
                boolean upStatus = lotteryPeriodService.drawPeriod(category, null, issure, resultCode);
                if (!upStatus) {
                    log.error("彩种:" + category.getName() + " 期号 " + issure + " 开奖结果 " + resultCode + " drawPeriod.error");
                    return;
                }
            }


        } catch (Exception e) {
            log.error(category.getName() + "开奖失败 drawPeriod.error", e);
        }
    }

    private void drawPeriodList(LotteryCategoryEnum category, String lotteryCode, String date) {
        String url = resultListMap.get(category);
        String resultContent = null;
        if (StringUtils.isNotBlank(url)) {
            url = MessageFormat.format(url, date);
            resultContent = httpClientUtil.sendHttpGet(url);
        } else {
            resultContent = httpClientUtil.sendHttpGet("https://api.api861861.com/lotteryJSFastThree/getJSFastThreeList.do?date=" + date + "&lotCode=" + lotteryCode);
        }
        try {
            if (!resultContent.startsWith("{")) {
                log.error("彩种:" + category.getName() + " list 彩集失败");
                return;
            }
            JSONObject jsonObject = JSONObject.fromObject(resultContent);
            if (jsonObject.getInt("errorCode") != 0) {
                throw new ApplicationException(category.getName() + ":获取开奖信息失败");
            }
            jsonObject = jsonObject.getJSONObject("result");
            if (!jsonObject.has("data")) {
                log.error("获取开奖数据失败:" + category.getName());
                return;
            }
            if (!jsonObject.has("data")) {
                log.error("获取开奖数据失败:" + category.getName() + " data:" + jsonObject.toString());
                return;
            }
            JSONArray list = jsonObject.getJSONArray("data");
            for (Object dataItem : list) {
                JSONObject object = (JSONObject) dataItem;
                String resultDate = object.getString("preDrawTime");
                String result = object.getString("preDrawCode");
                boolean resultData = lotteryPeriodService.saveByResultDateTime(category, null, resultDate, result);
                if (!resultData) {
                    String issure = object.getString("preDrawIssue");
                    if (!issure.startsWith("20")) {
                        issure = "20" + issure;
                    }
                    boolean b = lotteryPeriodService.drawPeriod(category, null, issure, result);
                    if (!b) {
                        log.error("彩种:" + category.getName() + " 期号 " + issure + " 开奖结果 " + result + " drawPeriodList.error");
                    }
                }
            }
        } catch (Exception e) {
            log.error("彩种:" + category.getName() + " drawPeriodList.error", e);
        }
    }

    private LotteryPeriod findLottery(LotteryCategoryEnum category) {
        Page<LotteryPeriod> lotteryPeriods = null;
        LotteryPeriod periodQuery = new LotteryPeriod();
        periodQuery.setLotteryType(category.getValue());
        periodQuery.setOpenStatus(YesOrNoEnum.NO.getValue());
        periodQuery.setOrderColumn("resultDate");
        periodQuery.setOrderType(OrderTypeEnum.ASC);
        lotteryPeriods = lotteryPeriodService.queryByPage(periodQuery, PageRequest.of(0, 1));
        if (lotteryPeriods.hasContent()) {
            return lotteryPeriods.getContent().get(0);
        } else {
            throw new ApplicationException(category.getName() + ":获取期号失败");
        }
    }
}
