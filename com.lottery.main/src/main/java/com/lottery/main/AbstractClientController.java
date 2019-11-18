package com.lottery.main;

import com.common.exception.BizException;
import com.common.util.RPCResult;
import com.common.util.StringUtils;
import com.common.web.AbstractController;
import com.lottery.service.RPCBeans;
import com.passport.rpc.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

/**
 * Created by shilun on 2017/5/12.
 */
@Slf4j
public abstract class AbstractClientController extends AbstractController {

    @Resource
    private RPCBeans rpcBeans;

    protected UserDTO getUser() {
        String token = getToken();
        RPCResult<UserDTO> userDTOResult = rpcBeans.getAdminRPCService().verificationToken(token);
        if (userDTOResult.getSuccess()) {
            return userDTOResult.getData();
        }
        log.error("token.error:" + token);
        throw new BizException("token.error", "获取token失败");
    }

    protected String getToken() {
        String token = getRequest().getHeader("token");
        if (StringUtils.isBlank(token)) {
            Cookie tokenCookie = null;
            for (Cookie item : getRequest().getCookies()) {
                if (StringUtils.equals(item.getName(), "token")) {
                    tokenCookie = item;
                    break;
                }
            }
            token = tokenCookie.getValue();
        }
        return token;
    }

    protected String getPin() {
        String token = getToken();
        RPCResult<UserDTO> userDTOResult = rpcBeans.getAdminRPCService().verificationToken(token);
        if (userDTOResult.getSuccess()) {
            return userDTOResult.getData().getPin();
        }
        log.error("token.error:" + token);
        throw new BizException("pin.error", "获取pin失败");
    }

    protected String getVer() {
        String ver = getRequest().getHeader("ver");
        if (StringUtils.isBlank(ver)) {
            throw new BizException("ver.null", "获取当前版本信息失败");
        }
        return ver;
    }
}
