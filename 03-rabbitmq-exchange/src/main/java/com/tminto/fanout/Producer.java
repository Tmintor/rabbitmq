package com.tminto.fanout;

import com.rabbitmq.client.Channel;
import com.tminto.util.RabbitMqUtil;

import java.util.Scanner;

/**
 * @author 吴员外
 * @date 2022/11/9 23:58
 */
public class Producer {


    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("发送消息成功");
        }
    }

}
