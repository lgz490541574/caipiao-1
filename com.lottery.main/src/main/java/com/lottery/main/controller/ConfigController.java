package com.lottery.main.controller;

import com.common.annotation.RoleResource;
import com.common.exception.BizException;
import com.common.util.BeanCoper;
import com.lottery.domain.Config;
import com.lottery.main.AbstractClientController;
import com.lottery.main.controller.dto.ConfigDto;
import com.lottery.service.ConfigService;
import com.lottery.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
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
     * @param info
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/config/list")
    public Map<String, Object> list(@RequestBody ConfigDto info) {
        return buildMessage(() -> {
            Config entity = new Config();
            BeanCoper.copyProperties(entity, info);
            List<Config> configs = configService.query(entity);
            return configs;
        });
    }

    /**
     * 查询
     * @param params
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/config/view")
    public Map<String, Object> view(@RequestBody Map<String, Object> params) {
        return buildMessage(() -> {
            Config byId = configService.findById((String) params.get("id"));
            if (!byId.getProxyId().equalsIgnoreCase(getUser().getProxyId())) {
                throw new BizException("data.error", "非法操作");
            }
            return byId;
        });
    }

    /**
     * 保存
     *
     * @param info
     * @return
     */
    @RoleResource(resource = "lottery")
    @RequestMapping("/config/save")
    public Map<String, Object> save(@RequestBody ConfigDto info) {
        return buildMessage(() -> {
            Config entity = new Config();
            BeanCoper.copyProperties(entity, info);
            configService.saveOrUpdate(entity);
            return null;
        });
    }


}
