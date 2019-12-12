package com.lottery.main.job;

import com.common.util.DateUtil;
import com.common.util.StringUtils;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.IssureRule;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.PeriodDayLogService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class IssureCreateUtils {

    @Resource
    private PeriodDayLogService periodDayLogService;

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    /**
     * 添加彩期
     *
     * @param category
     * @param totalCount
     * @param startHours
     * @param startMinutes
     * @param longTime
     */
    public void insertPeriod(LotteryCategoryEnum category, Integer totalCount, Integer startHours, Integer startMinutes, Integer longTime) {
        insertPeriod(category, totalCount, startHours, startMinutes, longTime, null);
    }

    /**
     * 添加彩期
     *
     * @param category
     * @param totalCount
     * @param startHours
     * @param startMinutes
     * @param longTime
     */
    public void insertPeriod(LotteryCategoryEnum category, Integer totalCount, Integer startHours, Integer startMinutes, Integer longTime, String proxyId) {
        DateTime time = new DateTime(DateUtil.getStartDate(DateTime.now().toDate()));
        IssureRule rule = category.getRule();
        time = time.plusDays(1);
        time = time.plusHours(startHours);
        time = time.plusMinutes(startMinutes);
        Integer startSeconds = rule.getStartSeconds();
        time = time.plusSeconds(startSeconds);
        String code = DateUtil.formatDateTime(time.toDate(), "yyyyMMdd");
        for (int i = 0; i < totalCount; i++) {
            DateTime endTime = time.plusSeconds(-10);
            DateTime openTime = time.plusMinutes(0);

            LotteryPeriod period = new LotteryPeriod();
            period.setLotteryType(category.getValue());
            period.setPrivateLottery(category.getPrivateLottery().getValue());
            if (StringUtils.isNotBlank(rule.getCodeFormat())) {
                period.setCode(code + String.format(rule.getCodeFormat(), i + 1));
            } else {
                period.setCode(code + getCode(i, rule.getCount()));
            }
            period.setEndOrderTime(endTime.toDate());
            period.setResultDate(openTime.toDate());
            period.setResultDateStr(DateUtil.formatDateTime(period.getResultDate(),"yyyy-MM-dd HH:mm:ss"));
            period.setResultStatus(YesOrNoEnum.NO.getValue());
            period.setOpenStatus(YesOrNoEnum.NO.getValue());
            period.setSettleStatus(YesOrNoEnum.NO.getValue());
            period.setProxyId(proxyId);
            time = time.plusMinutes(longTime);
            try {
                lotteryPeriodService.insert(period);

            } catch (DuplicateKeyException dupError) {
                log.warn("dup.period,type={},proxyId={}", new Object[]{category.getName(), proxyId});
            }
            if (category == LotteryCategoryEnum.SSC_CQ && i == 8) {
                time = time.plusHours(4).plusSeconds(25);
            }
        }
//        }
    }

    private String getCode(int index, int issureCount) {
        if (issureCount > 99) {
            return String.format("%03d", index + 1);
        }
        return String.format("%02d", index + 1);
    }

}
