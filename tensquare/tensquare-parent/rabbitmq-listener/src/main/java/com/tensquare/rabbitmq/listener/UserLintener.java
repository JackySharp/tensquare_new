package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "role-user")
public class UserLintener {

    private static final String name = "USER";

    @RabbitHandler
    public void recieveFanoutMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
