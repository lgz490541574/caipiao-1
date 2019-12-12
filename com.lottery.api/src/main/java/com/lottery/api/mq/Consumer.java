package com.lottery.api.mq;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Configurable
public class Consumer {

    @Resource
    protected SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 中奖同步
     */
    @RocketMQMessageListener(topic = "lottery-prize", consumerGroup = "lottery-group", messageModel = MessageModel.BROADCASTING)
    @Service
    public class LotteryPrizeConsumer implements RocketMQListener<String> {
        public void onMessage(String message) {
            JSONObject object = JSONObject.fromObject(message);
            String pin = object.getString("pin");
            simpMessagingTemplate.convertAndSendToUser(pin, "/prize", message);
        }
    }

    /**
     * 期号开始
     */
    @RocketMQMessageListener(topic = "period-open", consumerGroup = "lottery-group", messageModel = MessageModel.BROADCASTING)
    @Service
    public class PeriodConsumer implements RocketMQListener<String> {
        public void onMessage(String message) {
            simpMessagingTemplate.convertAndSend("/period/open", message);
        }
    }
}
