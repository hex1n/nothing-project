package com.hexin.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * @Author hex1n
 * @Date 2023/12/3/22:47
 * @Description
 **/
@Configuration
public class TransactionConfig {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Bean
    public PlatformTransactionManager transactionManager() {
        PlatformTransactionManager platformTransactionManager = new DataSourceTransactionManager(jdbcTemplate.getDataSource());
        return platformTransactionManager;
    }
}
