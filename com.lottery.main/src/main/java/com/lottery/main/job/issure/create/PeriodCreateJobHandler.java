package com.lottery.main.job.issure.create;

import com.common.util.DateUtil;
import com.common.util.RPCResult;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.JobErrorInfo;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.IssureRule;
import com.lottery.service.JobErrorService;
import com.lottery.service.LotteryPeriodService;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JobHandler(value = "PeriodCreateJobHandler")
@Component
public class PeriodCreateJobHandler extends IJobHandler {

    @Resource
    private LotteryPeriodService lotteryPeriodService;
    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;
    @Resource
    private JobErrorService jobErrorService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();
        RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
        List<ProxyDto> list = new ArrayList<>();
        if (listRPCResult.getSuccess()) {
            list = listRPCResult.getData();
        }
        for (LotteryCategoryEnum category : lotteryCategory) {
            if(category==LotteryCategoryEnum.LHC_XG){
                continue;
            }
            IssureRule rule = category.getRule();
            if (category.getPrivateLottery() == YesOrNoEnum.YES) {
                for (ProxyDto dto : list) {
                    try {
                        insertPeriod(category, rule.getCount(), rule.getStartHoure(), rule.getStartMins(), rule.getLongTime(), dto.getId());
                    }
                    catch (Exception e){
                        JobErrorInfo entity = new JobErrorInfo();
//                        entity.setJobType();
                        jobErrorService.insert(entity);
                    }
                }
            } else {
                insertPeriod(category, rule.getCount(), rule.getStartHoure(), rule.getStartMins(), rule.getLongTime());
            }

        }
        return SUCCESS;
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
            lotteryPeriodService.insert(period);
        }
    }

    private String getCode(int index) {
        return String.format("%03d", index + 1);
    }
}
