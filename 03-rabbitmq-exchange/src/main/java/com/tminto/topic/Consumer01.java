package com.tminto.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tminto.util.RabbitMqUtil;

/**
 * @author 吴员外
 * @date 2022/11/10 0:04
 */
public class Consumer01 {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare("Q1", false, false, false, null);

        channel.queueBind("Q1", EXCHANGE_NAME, "*.*.rabbit");

        DeliverCallback callback = ((consumerTag, message) -> {
            System.out.println("消费者C1收到消息:" + message);
        });
        channel.basicConsume("Q1",false,callback,(consumerTag -> {}));
    }

}
