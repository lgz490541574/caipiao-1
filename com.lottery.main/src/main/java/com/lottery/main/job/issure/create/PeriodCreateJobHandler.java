package com.lottery.main.job.issure.create;

import com.common.util.DateUtil;
import com.common.util.RPCResult;
import com.common.util.model.YesOrNoEnum;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.PeriodDayLog;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.domain.util.IssureRule;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.PeriodDayLogService;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JobHandler(value = "PeriodCreateJobHandler")
@Slf4j
@Component
public class PeriodCreateJobHandler extends IJobHandler {


    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;
    @Resource
    private PeriodDayLogService periodDayLogService;
    @Resource
    private IssureCreateUtils issureCreateUtils;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();
        RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
        List<ProxyDto> list = new ArrayList<>();
        if (listRPCResult.getSuccess()) {
            list = listRPCResult.getData();
        }
        String day = DateUtil.formatYYYYMMDD(new Date());
        for (LotteryCategoryEnum category : lotteryCategory) {
            if (category == LotteryCategoryEnum.LHC_XG) {
                continue;
            }
            IssureRule rule = category.getRule();
            if(rule==null){
                continue;
            }
            //判断数据是否已经存在
            PeriodDayLog periodDayLog = periodDayLogService.findByCategoryAndDay(category, day);
            if (periodDayLog != null && periodDayLog.getErrorStatus().intValue() == YesOrNoEnum.NO.getValue()) {
                continue;
            }
            if (category.getPrivateLottery() == YesOrNoEnum.YES) {
                for (ProxyDto dto : list) {
                    try {
                        issureCreateUtils.insertPeriod(category, rule.getCount(), rule.getStartHoure(), rule.getStartMins(), rule.getLongTime(), dto.getId());
                    } catch (Exception e) {
                        periodDayLog = new PeriodDayLog();
                        periodDayLog.setDay(day);
                        periodDayLog.setLotteryCategory(category.getValue());
                        periodDayLog.setErrorStatus(YesOrNoEnum.YES.getValue());
                        periodDayLogService.insert(periodDayLog);
                    }
                }
            } else {
                issureCreateUtils.insertPeriod(category, rule.getCount(), rule.getStartHoure(), rule.getStartMins(), rule.getLongTime());
            }
        }
        return SUCCESS;
    }

}
