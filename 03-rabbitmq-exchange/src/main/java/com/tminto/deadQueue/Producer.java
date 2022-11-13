package com.tminto.deadQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.tminto.util.RabbitMqUtil;

import java.util.Scanner;

/**
 * @author 吴员外
 * @date 2022/11/9 23:58
 */
public class Producer {

    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtil.getChannel();

        Scanner scanner = new Scanner(System.in);
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                .builder()
                .expiration("10000")
                .build();

        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(NORMAL_EXCHANGE,"normal", basicProperties, message.getBytes());
            System.out.println("发送消息成功");
        }
    }

}
