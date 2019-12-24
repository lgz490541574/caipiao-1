package com.lottery.api.mq;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 开奖
 */
@RocketMQMessageListener(topic = "lottery-open-topic", consumerGroup = "lottery-open-consumer", messageModel = MessageModel.BROADCASTING)
@Service
public class LotteryOpenConsumer implements RocketMQListener<String> {
    @Resource
    protected SimpMessagingTemplate simpMessagingTemplate;

    public void onMessage(String message) {
        simpMessagingTemplate.convertAndSend("/period/open", message);
    }

}