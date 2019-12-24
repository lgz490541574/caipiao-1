package com.lottery.api.mq;

import net.sf.json.JSONObject;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 中奖
 */
@RocketMQMessageListener(topic = "lottery-prize-topic", consumerGroup = "lottery-prize-consumer", messageModel = MessageModel.BROADCASTING)
@Service
public class LotteryPrizeConsumer implements RocketMQListener<String> {
    @Resource
    protected SimpMessagingTemplate simpMessagingTemplate;

    public void onMessage(String message) {
        JSONObject object = JSONObject.fromObject(message);
        String pin = object.getString("pin");
        simpMessagingTemplate.convertAndSendToUser(pin, "/prize", message);
    }
}
