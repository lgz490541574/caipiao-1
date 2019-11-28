package com.lottery.api.controller;

import com.lottery.api.AbstractClientController;
import com.lottery.api.controller.dto.OrderDto;
import com.lottery.service.OrderService;
import com.passport.rpc.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(method = {RequestMethod.POST})
public class OrderController extends AbstractClientController {
    @Resource
    private OrderService orderService;
    @RequestMapping("order/create")
    public Map<String, Object> create(@RequestBody OrderDto dto) {
        return buildMessage(() -> {
            UserDTO userDto = getUserDto();
            return orderService.createOrder(userDto.getProxyId(), userDto.getPin(), dto.getType(), dto.getPlayType(), dto.getPeriodCode(), dto.getCodes(), dto.getTimes(), dto.getOrderMoney(), dto.getChaseMark(), dto.getPrizeStop());
        });
    }

}
