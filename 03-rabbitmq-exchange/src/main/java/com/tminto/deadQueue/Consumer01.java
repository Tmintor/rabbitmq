package com.tminto.deadQueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tminto.util.RabbitMqUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 三种情况消息会进入死信队列：
 *              1.TTL到期
 *              2.队列达到最大长度
 *              3.
 */
public class Consumer01 {

    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        //将正常队列绑定死信队列的参数
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "dead");

        //声明正常交换机和死信交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        //声明正常队列和死信队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        //绑定队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead");

        DeliverCallback callback = ((consumerTag, message) -> {
            System.out.println("消费者C1收到消息:" + new String(message.getBody()));
        });
        channel.basicConsume(NORMAL_QUEUE,true,callback,(consumerTag -> {}));
    }

}
