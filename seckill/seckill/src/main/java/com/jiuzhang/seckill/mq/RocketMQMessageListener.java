package com.jiuzhang.seckill.mq;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
@org.apache.rocketmq.spring.annotation.RocketMQMessageListener(topic = "test-jiuzhang", consumerGroup = "conmuserGropjiuzhang")
public class RocketMQMessageListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {

        try {
            String body = new String(messageExt.getBody(), "UTF-8");
            System.out.println("receive message:" + body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
