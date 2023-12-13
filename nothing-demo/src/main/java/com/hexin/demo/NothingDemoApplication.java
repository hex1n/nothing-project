package com.hexin.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.management.ManagementFactory;


@SpringBootApplication
@Slf4j
@MapperScan(basePackages = "com.hexin.demo.mapper")
@EnableAsync
public class NothingDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NothingDemoApplication.class, args);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyListener() {
        return event -> {
            // 获取当前进程ID
            String processName = ManagementFactory.getRuntimeMXBean().getName();
            String processId = processName.split("@")[0];
            // 打印进程ID到控制台
            log.info("======NothingDemoApplication success started PID:{}", processId);
        };
    }
}
