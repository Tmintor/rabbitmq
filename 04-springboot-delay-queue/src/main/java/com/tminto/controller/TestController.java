package com.tminto.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 吴员外
 * @date 2022/11/13 11:27
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test/{msg}")
    public void test(@PathVariable String msg) {
        rabbitTemplate.convertAndSend("delayed-exchange","delayed.routingKey",msg);
        log.info("当前时间{}发送消息{}", new Date().toString(), msg);
    }
}
