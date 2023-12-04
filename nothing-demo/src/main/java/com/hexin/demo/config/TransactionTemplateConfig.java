package com.hexin.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @Author hex1n
 * @Date 2023/12/4/21:25
 * @Description
 **/
@Configuration
public class TransactionTemplateConfig {

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Bean(value = "transactionTemplateDefault")
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return transactionTemplate;
    }

    @Bean("transactionTemplateNew")
    public TransactionTemplate transactionTemplateNew() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return transactionTemplate;
    }
}
