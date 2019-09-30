package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "warm")
public class WarmListener {

    private static final String name = "WARM";

    @RabbitHandler
    public void recieveTopicMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
