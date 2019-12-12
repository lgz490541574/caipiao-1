package com.lottery.api.application;

import com.common.exception.BizException;
import com.common.util.RPCResult;
import com.common.util.UrlUtiles;
import com.lottery.api.controller.dto.UserPrincipal;
import com.passport.rpc.UserRPCService;
import com.passport.rpc.dto.UserDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Reference
    private UserRPCService userRPCService;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                URI uri = request.getURI();
                String token = UrlUtiles.getUrlParams(uri.toString(), "token");
                RPCResult<UserDTO> result = userRPCService.verificationToken(token);
                if (result.getSuccess()) {
                    return new UserPrincipal(result.getData());
                }
                throw new BizException("token.error", "用户登录失效");
            }
        }).setAllowedOrigins("*").withSockJS();
    }

}