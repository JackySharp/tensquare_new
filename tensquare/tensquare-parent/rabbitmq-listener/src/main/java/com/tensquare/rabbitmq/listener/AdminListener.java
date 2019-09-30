package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "role-admin")
public class AdminListener {

    private static final String name = "ADMIN";

    @RabbitHandler
    public void recieveFanoutMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
