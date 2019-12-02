package com.lottery.api.controller.web;

import com.common.util.ContentDto;
import com.lottery.api.AbstractSocketController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class LoginController extends AbstractSocketController {
    @MessageMapping("/login/in")
    @SendTo("/topic/login/in")
    public Map<String, Object> in(ContentDto dto) {
        return buildMessage(() -> {
            return null;
        });
    }
}
