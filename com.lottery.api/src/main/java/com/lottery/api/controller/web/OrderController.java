package com.lottery.api.controller.web;

import com.common.exception.BizException;
import com.lottery.api.AbstractClientController;
import com.lottery.api.controller.dto.OrderDto;
import com.lottery.api.controller.dto.TTDto;
import com.lottery.service.OrderService;
import com.passport.rpc.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
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
            return orderService.createOrder(userDto.getProxyId(), userDto.getPin(), dto.getType(), dto.getPlayType(), dto.getPeriodCode(), dto.getCodeList(), dto.getTimes(), dto.getChaseMark(), dto.getPrizeStop());
        });
    }
    @RequestMapping("order/sss")
    public Map<String,Object> tt(){
        Map<String,Object> result=new HashMap<>();
        result.put("date",new Date());
        return result;
    }

    @RequestMapping("order/ssss")
    public Map<String,Object> ttss(@RequestBody TTDto date){
        Map<String,Object> result=new HashMap<>();
        result.put("date",date);
        throw new BizException("fdsafds");
    }

}
