package com.lottery.proxy.controller;

import com.common.annotation.RoleResource;
import com.common.util.BeanCoper;
import com.common.util.GlosseryEnumUtils;
import com.lottery.domain.LotteryPeriod;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.proxy.AbstractClientController;
import com.lottery.service.LotteryPeriodService;
import com.lottery.service.dto.LotteryPeriodDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(method = {RequestMethod.POST})
public class LotteryController extends AbstractClientController {
    @Resource
    private LotteryPeriodService lotteryPeriodService;

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/lottery/list")
    public Page<LotteryPeriodDto> list(@RequestBody LotteryPeriodDto dto) {
        String proxyId = getUser().getProxyId();
        dto.setProxyId(proxyId);
        Page<LotteryPeriod> page = lotteryPeriodService.queryByPage(BeanCoper.copyProperties(LotteryPeriod.class, dto), dto.getPageinfo().getPage());
        for(LotteryPeriod item:page.getContent()){
            item.setLotteryType(dto.getLotteryType());
        }
        return clone(LotteryPeriodDto.class, page);
    }

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/lottery/view")
    public LotteryPeriodDto view(@RequestBody LotteryPeriodDto dto) {
        LotteryPeriod period = clone(LotteryPeriod.class, dto);
        String proxyId = getUser().getProxyId();
        period.setProxyId(proxyId);
        Page<LotteryPeriod> lotteryPeriods = lotteryPeriodService.queryByPage(period, PageRequest.of(0, 1));
        LotteryPeriod source = lotteryPeriods.getContent().get(0);
        LotteryCategoryEnum category= GlosseryEnumUtils.getItem(LotteryCategoryEnum.class,dto.getLotteryType());
        source.setPrivateLottery(category.getPrivateLottery().getValue());
        return clone(LotteryPeriodDto.class, source);
    }

    /**
     * 保存
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/lottery/save")
    public void save(@RequestBody LotteryPeriodDto dto) {
        LotteryPeriod entity = clone(LotteryPeriod.class, dto);
        String proxyId = getUser().getProxyId();
        entity.setProxyId(proxyId);
        lotteryPeriodService.save(entity);
    }


}
