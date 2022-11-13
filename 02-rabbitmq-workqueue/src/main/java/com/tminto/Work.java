package com.tminto;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.tminto.util.RabbitMqUtil;

/**
 * @author 吴员外
 * @date 2022/10/24 0:07
 */

public class Work {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();

        //消费成功的回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("消息是：" + new String(message.getBody()));

            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        //中断的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费消息被中断");
        };

        System.out.println("C2等待消息中.....");

        channel.basicConsume(QUEUE_NAME, false, deliverCallback, cancelCallback);
    }


}
