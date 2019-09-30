package com.tensquare.user.productor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQProductor {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void directMod() {
        rabbitTemplate.convertAndSend("","test-direct","直连模式测试");
    }

    @Test
    public void fanoutMod() {
        rabbitTemplate.convertAndSend("roles","","分裂模式测试");
    }

    @Test
    public void topicMod() {
        rabbitTemplate.convertAndSend("temp","a.b","主题模式测试");
    }

}
