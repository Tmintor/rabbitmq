package com.tminto.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 *  基于死信队列实现延迟队列
 */
@Configuration
public class DelayedQueueConfig {

    public static final String DELAYED_EXCHANGE = "delayed-exchange";

    public static final String DELAYED_QUEUE = "delayed-queue";

    public static final String DELAYED_ROUTING = "delayed.routingKey";

    public static final String DEAD_LETTER_QUEUE = "dead-letter-queue";

    public static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";

    public static final String DEAD_LETTER_ROUTING_KEY = "dead.letter.routingKey";

    /**
     * 声明死信交换机
     */
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    /**
     * 死信队列
     */
    @Bean
    public Queue deadLetterQueue(){
        return new Queue(DEAD_LETTER_QUEUE);
    }

    /**
     *  延迟交换机
     */
    @Bean
    public DirectExchange delayedExchange() {
        return new DirectExchange(DELAYED_EXCHANGE);
    }

    /**
     * 延迟队列
     */
    @Bean
    public Queue delayedQueue(){
        HashMap<String, Object> hashMap = new HashMap<>(2);
        //这里声明当前队列绑定的死信交换机
        hashMap.put("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE);
        //这里声明当前队列的死信路由key
        hashMap.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        // x-message-ttl  声明队列的TTL
        hashMap.put("x-message-ttl",10000);
        return QueueBuilder.nonDurable(DELAYED_QUEUE).withArguments(hashMap).build();
    }

    /**
     * 绑定死信队列
     */
    @Bean
    public Binding deadQueueBinding(@Qualifier("deadLetterQueue") Queue deadLetterQueue,
                                    @Qualifier("deadLetterExchange") DirectExchange deadLetterExchange) {

        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY);
    }

    /**
     * 绑定延迟队列
     */
    @Bean
    public Binding delayedBinding(@Qualifier("delayedQueue") Queue delayedQueue,
                                  @Qualifier("delayedExchange") DirectExchange delayedExchange) {

        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING);
    }




}
