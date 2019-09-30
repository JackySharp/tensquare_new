package com.tensquare.split;

import com.tensquare.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class SplitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SplitApplication.class,args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(4L,0L);
    }
}
