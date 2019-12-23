package com.lottery.proxy.controller;

import com.common.annotation.RoleResource;
import com.common.exception.BizException;
import com.common.util.BeanCoper;
import com.common.util.GlosseryEnumUtils;
import com.common.util.IdDto;
import com.common.util.StringUtils;
import com.lottery.domain.Config;
import com.lottery.domain.model.LotteryCategoryEnum;
import com.lottery.proxy.AbstractClientController;
import com.lottery.proxy.controller.dto.ConfigDto;
import com.lottery.service.ConfigService;
import com.lottery.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(method = {RequestMethod.POST})
public class ConfigController extends AbstractClientController {
    @Resource
    private ConfigService configService;
    @Resource
    private OrderService orderService;

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/config/list")
    public Map<String, Object> list(@RequestBody ConfigDto dto) {
        return buildMessage(() -> {
            Config entity = new Config();
            BeanCoper.copyProperties(entity, dto);
            dto.setPorxyId(getUser().getProxyId());
            return configService.queryByPage(entity,dto.getPageinfo().getPage());
        });
    }

    /**
     * 查询
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/config/view")
    public Map<String, Object> view(@RequestBody IdDto dto) {
        return buildMessage(() -> {
            Config byId = configService.findById(dto.getId());
            if (!byId.getProxyId().equalsIgnoreCase(getUser().getProxyId())) {
                throw new BizException("data.error", "非法操作");
            }
            return byId;
        });
    }

    /**
     * 保存
     *
     * @param dto
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/config/save")
    public void save(@RequestBody ConfigDto dto) {
        Config entity = new Config();
        BeanCoper.copyProperties(entity, dto);
        LotteryCategoryEnum item = GlosseryEnumUtils.getItem(LotteryCategoryEnum.class, dto.getLotteryType());
        if (item.getParent() != null) {
            throw new BizException("data.error", "非法操作");
        }
        Enum[] enumConstants = (Enum[]) item.getPlayType().getEnumConstants();
        boolean playType = false;
        for (Enum enumItem : enumConstants) {
            if (enumItem.name().equals(dto.getKey())) {
                playType = true;
                break;
            }
        }
        if (playType == false) {
            throw new BizException("data.error", "非法操作");
        }
        entity.setProxyId(getUser().getProxyId());
        configService.saveOrUpdate(entity);
    }


}
