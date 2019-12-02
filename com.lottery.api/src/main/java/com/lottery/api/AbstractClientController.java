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
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by dabai on 2017/5/12.
 */
public abstract class AbstractClientController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractClientController.class);


    @Resource
    private RPCBeans rpcBeans;

    private Map<String, ProxyDto> proxyMap = new HashMap<>();

    protected UserDTO getUserDto() {
        if (getRequest().getSession().getAttribute("userDto") != null) {
            return (UserDTO) getRequest().getSession().getAttribute("userDto");
        }
        HttpServletRequest request = getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            Cookie tokenCookie = null;
            for (Cookie item : request.getCookies()) {
                if (StringUtils.equals(item.getName(), "token")) {
                    tokenCookie = item;
                    break;
                }
            }
            if (tokenCookie == null) {
                return null;
            }
            token = tokenCookie.getValue();
        }
        if (StringUtils.isBlank(token)) {
            return null;
        }
        RPCResult<UserDTO> result = rpcBeans.getUserRPCService().verificationToken(token);
        if (result.getSuccess()) {
            return result.getData();
        }
        return result.getData();
    }

    /**
     * 获取代理商信息
     *
     * @return
     */
    protected ProxyDto getDomain() {
        String domain = StringUtils.getDomain(getRequest().getRequestURL().toString());
        try {
            return cache.get(domain);
        } catch (Exception e) {
            LOGGER.error("domain error" + domain);
            LOGGER.error("url->" + getRequest().getRequestURL().toString());
            LOGGER.error("domain.error", e);
        }
        throw new BizException("server.domain.error:domain->" + domain, "服务器域名错误");

    }

    private LoadingCache<String, ProxyDto> cache = CacheBuilder.newBuilder().refreshAfterWrite(30, TimeUnit.SECONDS)
            .expireAfterAccess(30, TimeUnit.SECONDS).
                    build(new CacheLoader<String, ProxyDto>() {
                        @Override
                        /** 当本地缓存命没有中时，调用load方法获取结果并将结果缓存 **/
                        public ProxyDto load(String domain) {
                            RPCResult<ProxyDto> result = rpcBeans.getProxyInfoRPCService().findByDomain(domain);
                            if (result.getSuccess()) {
                                ProxyDto dto = result.getData();
                                proxyMap.put(domain, dto);
                                return dto;
                            }
                            return null;
                        }

                    });

    /**
     * 获取IP
     *
     * @return
     */
    protected String getIP() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
