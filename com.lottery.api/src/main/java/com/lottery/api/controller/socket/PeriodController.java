package com.lottery.api.controller.socket;

import com.common.util.GlosseryEnumUtils;
import com.lottery.api.AbstractClientController;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.service.LotteryPeriodService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class PeriodController extends AbstractClientController {

    @Resource
    private LotteryPeriodService lotteryPeriodService;

    @MessageMapping("/period/subscribe/{type}")
    @SendTo("/topic/period/last")
    public Map<String, Object> view(@PathVariable String type) {
        return buildMessage(() -> {
            return lotteryPeriodService.findLast(GlosseryEnumUtils.getItem(LotteryCategoryEnum.class,type), getDomain().getId());
        });
    }

}
