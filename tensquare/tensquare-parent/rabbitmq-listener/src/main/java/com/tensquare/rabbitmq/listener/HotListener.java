package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "hot")
public class HotListener {

    private static final String name = "HOT";

    @RabbitHandler
    public void recieveTopicMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
