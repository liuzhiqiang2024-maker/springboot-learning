package com.example.springbootdemo.service;

import com.example.springbootdemo.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {

    // 监听 test.queue 队列，一旦有消息，就会自动执行这个方法
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        log.info("【消费者收到消息】：{}", message);
        // 在这里可以写你的业务逻辑，比如：发邮件、发短信、异步更新数据库
    }
}