package com.lottery.main.job.issure.create;

import com.common.util.DateUtil;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
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
        DateTime time = new DateTime(DateUtil.getStartDate(new Date()));
        time = time.plusDays(1);
        time = time.plusHours(startHours);
        time = time.plusMinutes(startMinutes);
        String code = DateUtil.formatDateTime(time.toDate(), "yyMMdd");
        for (int i = 0; i < totalCount; i++) {
            DateTime endTime = time.plusSeconds(-10);
            DateTime openTime = time.plusMinutes(0);
            LotteryPeriod period = new LotteryPeriod();
            period.setLotteryType(category.getValue());
            period.setPrivateLottery(category.getPrivateLottery().getValue());
            period.setCode(code + getCode(i));
            period.setEndOrderTime(endTime.toDate());
            period.setResultDate(openTime.toDate());
            period.setResultStatus(YesOrNoEnum.NO.getValue());
            period.setStatus(YesOrNoEnum.NO.getValue());
            period.setSettleStatus(YesOrNoEnum.NO.getValue());
            period.setProxyId(proxyId);
            time = time.plusMinutes(longTime);
            try {
                lotteryPeriodService.insert(period);
            } catch (DuplicateKeyException dupError) {
                log.warn("dup.period,type={},proxyId={}", new Object[]{category.getName(), proxyId});
            }
        }
    }

    private String getCode(int index) {
        return String.format("%03d", index + 1);
    }
}
