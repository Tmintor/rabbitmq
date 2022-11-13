package com.tminto.direct;

import com.rabbitmq.client.Channel;
import com.tminto.util.RabbitMqUtil;

import java.util.Scanner;

/**
 * @author 吴员外
 * @date 2022/11/9 23:58
 */
public class Producer {


    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtil.getChannel();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes());
            System.out.println("发送消息成功");
        }
    }

}
