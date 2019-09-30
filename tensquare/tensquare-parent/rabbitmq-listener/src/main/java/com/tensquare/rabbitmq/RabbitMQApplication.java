package com.tensquare.rabbitmq;

import com.tensquare.common.utils.SmsUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class,args);
    }

    @Bean
    public SmsUtil getBean() {
        return new SmsUtil();
    }
}
