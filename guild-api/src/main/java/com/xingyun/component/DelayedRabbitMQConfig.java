package com.xingyun.component;

import java.util.HashMap;
import java.util.Map;

//import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DelayedRabbitMQConfig {

    /**
     * 创建
     */
    public static final String DELAYED_QUEUE_NAME = "delay.queue.financial.delay.queue";
    public static final String DELAYED_EXCHANGE_NAME = "delay.queue.financial.delay.exchange";
    public static final String DELAYED_ROUTING_KEY = "delay.queue.financial.delay.routingkey";

    /*@Bean
    public Queue immediateQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding bindingNotify(@Qualifier("immediateQueue") Queue queue,
        @Qualifier("customExchange") CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }


    *//**
     * 转出
     *//*
    public static final String FIXED_ORDER_OUT_QUEUE_NAME = "delay.queue.financial.out.queue";
    public static final String FIXED_ORDER_OUT_EXCHANGE_NAME = "delay.queue.financial.out.exchange";
    public static final String FIXED_ORDER_OUT_ROUTING_KEY = "delay.queue.financial.out.routing";

    @Bean
    public Queue outQueue() {
        return new Queue(FIXED_ORDER_OUT_QUEUE_NAME);
    }

    @Bean
    public CustomExchange outExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(FIXED_ORDER_OUT_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    @Bean
    public Binding outBindingNotify(@Qualifier("outQueue") Queue queue,
                                 @Qualifier("outExchange") CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(FIXED_ORDER_OUT_ROUTING_KEY).noargs();
    }*/

}
