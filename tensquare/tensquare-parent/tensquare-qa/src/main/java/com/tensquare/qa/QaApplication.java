package com.tensquare.qa;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
public class QaApplication {
    public static void main(String[] args) {
        SpringApplication.run(QaApplication.class,args);
    }

    @Bean
    public IdWorker IdCreator() {
        return new IdWorker(2L,0L);
    }

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
