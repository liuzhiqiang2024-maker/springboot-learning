package com.example.springbootdemo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // 队列名称
    public static final String QUEUE_NAME = "test.queue";

    // 声明一个队列（重启项目后，它会自动在 RabbitMQ 后台创建出来）
    @Bean
    public Queue testQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}