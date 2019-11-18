package com.lottery.main.controller;

import com.common.exception.BizException;
import com.common.util.RPCResult;
import com.common.util.StringUtils;
import com.common.web.IExecute;
import com.lottery.main.AbstractClientController;
import com.lottery.main.controller.dto.LoginDto;
import com.lottery.main.controller.dto.PasswordChangeDto;
import com.lottery.service.ConfigService;
import com.lottery.service.RPCBeans;
import com.passport.rpc.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@RequestMapping(value = "/login", method = {RequestMethod.POST})
public class LoginController extends AbstractClientController {

    @Resource
    private ConfigService configService;
    @Resource
    private RPCBeans rpcBeans;
    @RequestMapping("in")
    public Map<String, Object> login(@RequestBody LoginDto dto, HttpServletResponse response) {
        return buildMessage(new IExecute() {
            @Override
            public Object getData() {
                RPCResult<UserDTO> result = rpcBeans.getAdminRPCService().login(dto.getAccount(), dto.getCode());
                putCookie("token", result.getData().getToken(), response);
                return result;
            }
        });
    }


    @RequestMapping("out")
    public Map<String,Object> loginOut() {
        return buildMessage(new IExecute() {
            @Override
            public Object getData() {
                return rpcBeans.getAdminRPCService().loginOut(getToken());
            }
        });
    }

    @RequestMapping("check")
    public Map<String, Object> check() {
        return buildMessage(new IExecute() {
            @Override
            public Object getData() {
                String token = getToken();
                if (StringUtils.isBlank(token)) {
                    throw new BizException("token.error", "token error");
                }
                RPCResult<UserDTO> userDTOResult = rpcBeans.getAdminRPCService().verificationToken(token);
                return userDTOResult.getSuccess();
            }
        });
    }

    @RequestMapping("changePass")
    public Map<String, Object> changePass(@RequestBody PasswordChangeDto dto) {
        return buildMessage(new IExecute() {
            @Override
            public Object getData() {
                return rpcBeans.getAdminRPCService().changePass(getPin(), dto.getOldPassword(), dto.getNewPassword());
            }
        });
    }

}
