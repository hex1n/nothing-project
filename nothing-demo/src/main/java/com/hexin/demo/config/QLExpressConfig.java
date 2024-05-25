package com.hexin.demo.config;

import com.ql.util.express.ExpressRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author hex1n
 * @Date 2024/5/25/21:30
 * @Description
 **/
@Configuration
public class QLExpressConfig {
    @Bean
    public ExpressRunner expressRunner() {
        return new ExpressRunner();
    }
}
