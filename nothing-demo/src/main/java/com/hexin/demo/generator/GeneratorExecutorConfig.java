package com.hexin.demo.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 代码生成器线程池配置
 */
@Configuration
@Slf4j
public class GeneratorExecutorConfig {
    
    @Bean(name = "generatorExecutor")
    public ExecutorService generatorExecutor() {
        int processors = Runtime.getRuntime().availableProcessors();
        int corePoolSize = Math.max(4, processors);
        int maxPoolSize = processors * 2;
        
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger counter = new AtomicInteger(1);
            
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("generator-thread-" + counter.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        };
        
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        
        executor.allowCoreThreadTimeOut(true);
        
        log.info("创建代码生成器线程池: corePoolSize={}, maxPoolSize={}", corePoolSize, maxPoolSize);
        
        return executor;
    }
}