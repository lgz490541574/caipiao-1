package com.lottery.service.impl;

import com.common.exception.ApplicationException;
import com.common.util.DateUtil;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.PeriodDayLog;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.PeriodDayLogService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class LHCPerodCreateUtil {
    private static final String LHC_URL = "https://www.kjrq.tv/1/{0}.html";

    private static final String hour = "21:20:00";

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    @Resource
    private PeriodDayLogService periodDayLogService;

    @Resource(name = "primary")
    protected MongoTemplate primaryTemplate;

    @Resource(name = "secondary")
    protected MongoTemplate secondaryTemplate;

    public void build(String month) {
        String day = DateUtil.formatYYYYMMDD(new Date());
        //判断数据是否已经存在
        PeriodDayLog periodDayLog = periodDayLogService.findByCategoryAndDay(LotteryCategoryEnum.LHC_XG, day);
        if (periodDayLog != null && periodDayLog.getErrorStatus().intValue() == YesOrNoEnum.NO.getValue().intValue()) {
            return;
        }
        LotteryCategoryEnum category = LotteryCategoryEnum.LHC_XG;
        String monthStr = month;
        List<Date> dates = crawlLhcOpenDate(monthStr);
        LotteryPeriod period = new LotteryPeriod();
        period.setLotteryType(category.getValue());
        Page<LotteryPeriod> lotteryPeriods = lotteryPeriodService.queryByPage(period, PageRequest.of(0, 1));

        Integer code = 0;
        if (lotteryPeriods.getContent() == null || lotteryPeriods.getContent().size() == 0) {
            code = 1;
        } else {
            code = Integer.parseInt(lotteryPeriods.getContent().get(0).getCode());
        }
        for (Date date : dates) {
            String collectionName = lotteryPeriodService.getCollectionName(category, null);
            period = new LotteryPeriod();
            period.setCode(String.valueOf(code));
            String dayItem = DateUtil.formatDateTime(date, "yyyy-MM-dd");
            DateTime openTime = new DateTime(DateUtil.parse(dayItem,"yyyy-MM-dd"));
            openTime = openTime.plusHours(21).plusMinutes(30);
            period.setEndOrderTime(openTime.plusMinutes(-30).toDate());
            period.setResultDate(openTime.toDate());
            period.setResultDateStr(DateUtil.formatDateTime(openTime.toDate(), "yyyy-MM-dd HH:mm:ss"));
            period.setLotteryType(category.getValue());
            period.setOpenStatus(YesOrNoEnum.NO.getValue());
            period.setResultStatus(YesOrNoEnum.NO.getValue());
            period.setPrivateLottery(YesOrNoEnum.NO.getValue());
            period.setSettleStatus(YesOrNoEnum.NO.getValue());
            primaryTemplate.insert(period, collectionName);
            code++;
        }

    }


    /**
     * 获取六合彩开奖日期
     *
     * @param month
     * @return
     */
    private static List<Date> crawlLhcOpenDate(String month) {
        Document document = null;
        try {
            String format = MessageFormat.format(LHC_URL, month);
            document = Jsoup.connect(format).get();
        } catch (Exception e) {
            log.error("crawl.lhc.open.date.error", e);
            throw new ApplicationException("crawl.lhc.open.date.error", e);
        }
        Elements test = document.select(".kjr");
        List<Date> list = new ArrayList<>();
        long now = System.currentTimeMillis();
        for (Element element : test) {
            String number = element.text();
            if (number.length() < 2) {
                number = "0" + number;
            }
            if (!"".equals(number)) {
                String date = month + "-" + number + " " + hour;
                Date ele = DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
                if (ele.getTime() > now) {
                    list.add(ele);
                }
            }
        }
        return list;

    }

}
