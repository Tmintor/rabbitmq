package com.tminto.sayhello;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author 吴员外
 * @date 2022/10/23 23:36
 */
public class Consumer {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.150.128");
        factory.setUsername("admin");
        factory.setPassword("admin");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //消费成功的回调
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println("消息是：" + new String(message.getBody()));
        };

        //中断的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费消息被中断");
        };


        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
