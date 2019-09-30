package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "test-direct")
public class DirectListener {

    private static final String name = "DIRECT_RECIEVER";

    @RabbitHandler
    public void recieveMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
