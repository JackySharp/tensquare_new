package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "role-seller")
public class SellerListener {

    private static final String name = "SELLER";

    @RabbitHandler
    public void recieveFanoutMsg(String message) {
        System.out.println(name + "接受到消息:" + message);
    }
}
