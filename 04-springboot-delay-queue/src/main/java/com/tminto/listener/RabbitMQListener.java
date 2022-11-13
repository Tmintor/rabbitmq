package com.tminto.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 吴员外
 * @date 2022/11/13 12:11
 */
@Slf4j
@Component
public class RabbitMQListener {

    @RabbitListener(queues = "dead-letter-queue")
    public void receive(Message message) {
        String msg = new String(message.getBody());
        log.info("当前时间{}接收到消息{}", new Date().toString(), msg);
    }
}
