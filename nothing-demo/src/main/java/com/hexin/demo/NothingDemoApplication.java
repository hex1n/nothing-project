package com.hexin.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@Slf4j
@MapperScan(value = "com.hexin.demo.mapper.*")
@EnableAsync
public class NothingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NothingDemoApplication.class, args);
        log.info("======NothingDemoApplication success start======");
    }

}
