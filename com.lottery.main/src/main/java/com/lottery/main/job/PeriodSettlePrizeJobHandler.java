package com.lottery.main.job;

import com.common.util.RPCResult;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.dto.ProxyDto;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 算奖
 */
@JobHandler(value = "PeriodSettlePrizeJobHandler")
@Slf4j
@Component
public class PeriodSettlePrizeJobHandler extends IJobHandler {

    @Resource
    private ProxyInfoRPCService proxyInfoRPCService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        LotteryCategoryEnum[] lotteryCategory = LotteryCategoryEnum.values();
        RPCResult<List<ProxyDto>> listRPCResult = proxyInfoRPCService.queryAll();
        List<ProxyDto> list = new ArrayList<>();
        if (listRPCResult.getSuccess()) {
            list = listRPCResult.getData();
        }

        return null;
    }
}
