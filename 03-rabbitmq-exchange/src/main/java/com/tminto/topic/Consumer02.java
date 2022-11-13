package com.tminto.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tminto.util.RabbitMqUtil;

/**
 * @author 吴员外
 * @date 2022/11/10 0:04
 */
public class Consumer02 {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        channel.queueDeclare("Q2", false, false, false, null);

        channel.queueBind("Q2", EXCHANGE_NAME, "tminto.#");


        DeliverCallback callback = ((consumerTag, message) -> {
            System.out.println("消费者C2收到消息:" + message);
        });
        channel.basicConsume("Q2",false,callback,(consumerTag -> {}));
    }

}
