package com.bins.springboot.dubbo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@EnableDubbo
@Slf4j
@MapperScan(value = "com.bins.springboot.dubbo.consumer.mappers.*")
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        log.info("======ConsumerApplication success start======");
    }

}
