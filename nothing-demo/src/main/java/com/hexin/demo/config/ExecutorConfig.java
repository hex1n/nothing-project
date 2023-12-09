package com.hexin.demo.config;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author hex1n
 * @Date 2021/11/5 16:59
 * @Description
 */
@EnableAsync
@Configuration
@Slf4j
public class ExecutorConfig {
    private int corePoolSize = 10;
    private int maxPoolSize = 20;
    private int queueCapacity = 5000;

    @Bean(name = "commentAsync")
    public Executor commentAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("commentAsync-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    @Bean(name = "eventExecutor")
    public Executor eventExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("eventExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        // 每秒产生100 个令牌
        RateLimiter limiter = RateLimiter.create(100);
        // 获取1 个令牌,如果获取到, 则立马返回; 否则阻塞(内部调用了 sleep 函数)
        // 等待新的令牌产生
        limiter.acquire(1);
        // 或者获取不到令牌,调用方不阻塞,直接返回
        limiter.tryAcquire(1);
        System.out.println();
    }


}
