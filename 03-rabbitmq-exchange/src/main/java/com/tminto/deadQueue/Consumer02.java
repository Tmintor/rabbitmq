package com.tminto.deadQueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tminto.util.RabbitMqUtil;

/**
 * @author 吴员外
 * @date 2022/11/10 0:04
 */
public class Consumer02 {

    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtil.getChannel();

        DeliverCallback callback = ((consumerTag, message) -> {
            System.out.println("收到死信队列消息:" + new String(message.getBody()));
        });
        channel.basicConsume(DEAD_QUEUE,true,callback,(consumerTag -> {}));
    }

}
