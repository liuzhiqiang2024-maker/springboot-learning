package com.example.springbootdemo.service;

import com.example.springbootdemo.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ArticleMQConsumer {

    // 1. 监听邮件队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_EMAIL)
    public void handleEmail(String message) {
        log.info("【邮件服务】收到消息，正在发送邮件：{}", message);
    }

    // 2. 监听站内信队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGE)
    public void handleMessage(String message) {
        log.info("【站内信服务】收到消息，正在给粉丝发站内信：{}", message);
    }

    // 3. 监听积分队列
    @RabbitListener(queues = RabbitMQConfig.QUEUE_POINTS)
    public void handlePoints(String message) {
        log.info("【积分服务】收到消息，正在给作者加积分：{}", message);
    }
}