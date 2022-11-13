package com.tminto;

import com.rabbitmq.client.Channel;
import com.tminto.util.RabbitMqUtil;

import java.util.Scanner;

/**
 * @author 吴员外
 * @date 2022/10/24 0:13
 */
public class Producer {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtil.getChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息：" + message);
        }
    }
}
