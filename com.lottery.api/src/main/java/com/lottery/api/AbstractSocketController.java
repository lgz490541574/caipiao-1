package com.lottery.api;

import com.common.exception.BizException;
import com.common.util.RPCResult;
import com.common.util.StringUtils;
import com.common.web.AbstractController;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.lottery.service.RPCBeans;
import com.passport.rpc.dto.ProxyDto;
import com.passport.rpc.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dabai on 2017/5/12.
 */
public abstract class AbstractSocketController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSocketController.class);


    @Resource
    protected RPCBeans rpcBeans;


}
