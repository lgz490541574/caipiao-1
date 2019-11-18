package com.lottery.service;

import com.passport.rpc.AdminRPCService;
import com.passport.rpc.ProxyInfoRPCService;
import com.passport.rpc.UserRPCService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;

@Component
public class RPCBeans {
    @Reference
    private AdminRPCService adminRPCService;

    @Reference
    private UserRPCService userRPCService;

    @Reference
    private ProxyInfoRPCService proxyInfoRPCService;

    public UserRPCService getUserRPCService() {
        return userRPCService;
    }

    public AdminRPCService getAdminRPCService() {
        return adminRPCService;
    }

    public ProxyInfoRPCService getProxyInfoRPCService() {
        return proxyInfoRPCService;
    }
}
