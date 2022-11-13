package com.tminto.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tminto.util.RabbitMqUtil;

/**
 * @author 吴员外
 * @date 2022/11/10 0:04
 */
public class Consumer02 {

    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.queueDeclare("disk", false, false, false, null);

        channel.queueBind("disk", EXCHANGE_NAME, "error");

        DeliverCallback callback = ((consumerTag, message) -> {
            System.out.println("消费者C2收到消息:" + new String(message.getBody()));
        });
        channel.basicConsume("disk",false,callback,(consumerTag -> {}));
    }

}
