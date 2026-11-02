package com.example.springbootdemo.config;
import org.springframework.amqp.core.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    // ========== 1. 声明常量 ==========
    public static final String EXCHANGE_DIRECT = "article.direct.exchange";
    public static final String QUEUE_EMAIL = "article.email.queue";
    public static final String ROUTING_KEY_EMAIL = "article.email";

    public static final String EXCHANGE_FANOUT = "article.fanout.exchange";
    public static final String QUEUE_MESSAGE = "article.message.queue";
    public static final String QUEUE_POINTS = "article.points.queue";
    // ========== 2. 声明交换机 ==========
    // Direct 交换机：根据路由键精确匹配
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_DIRECT,true,false);
    }
    // Fanout 交换机：广播，发给所有绑定的队列
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_FANOUT,true,false);
    }
    // ========== 3. 声明队列 ==========
    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_EMAIL,true);
    }
    @Bean
    public Queue messageQueue() {
        return new Queue(QUEUE_MESSAGE, true);
    }

    @Bean
    public Queue pointsQueue() {
        return new Queue(QUEUE_POINTS, true);
    }
    // ========== 4. 绑定关系 ==========
    // 1. 邮件队列绑定到 Direct 交换机，路由键为 article.email
    @Bean
    public  Binding bindEmailQueue(){
        return BindingBuilder.bind(emailQueue()).to(directExchange()).with(ROUTING_KEY_EMAIL);
    }
    // 2. 站内信队列绑定到 Fanout 交换机（不需要路由键）
    @Bean
    public  Binding bindMessageQueue(){
        return  BindingBuilder.bind(messageQueue()).to(fanoutExchange());
    }
    // 3. 积分队列也绑定到 Fanout 交换机
    @Bean
    public  Binding bindPointsQueue(){
        return BindingBuilder.bind(pointsQueue()).to(fanoutExchange());
    }
}