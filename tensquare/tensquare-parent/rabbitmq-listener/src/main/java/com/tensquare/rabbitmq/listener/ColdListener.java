package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "cold")
public class ColdListener {

    private static final String name = "COLD";

    @RabbitHandler
    public void recieveTopicMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
