package com.lottery.api.controller.socket;

import com.account.rpc.AccountRPCService;
import com.account.rpc.dto.AccountDto;
import com.common.exception.BizException;
import com.common.util.ContentDto;
import com.common.util.RPCResult;
import com.lottery.api.AbstractSocketController;
import com.passport.rpc.UserRPCService;
import com.passport.rpc.dto.UserDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class UserController extends AbstractSocketController {

    @Reference
    private AccountRPCService accountRPCService;
    @Reference
    private UserRPCService userRPCService;

    /**
     * 现金
     */
    private Integer tokenType = 1;

    @MessageMapping("/login/in/")
    @SendTo("/topic/user/account")
    public Map<String, Object> in(@RequestBody ContentDto dto) {
        return buildMessage(() -> {
            RPCResult<UserDTO> result = userRPCService.verificationToken(dto.getContent());
            String pin = null;
            if (!result.getSuccess()) {
                throw new BizException("login.error", "登录失效");
            }
            pin = result.getData().getPin();

            RPCResult<AccountDto> accountResult = accountRPCService.findAccount(pin, tokenType);
            if (!accountResult.getSuccess()) {
                throw new BizException("account.error", "获取账本失效");
            }
            return accountResult.getData().getAmount();
        });
    }


}
