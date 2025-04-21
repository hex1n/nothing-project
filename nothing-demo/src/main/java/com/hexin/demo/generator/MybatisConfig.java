package com.hexin.demo.generator;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MybatisConfig {
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        
        // 配置MyBatis的配置
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 允许JDBC生成主键
        configuration.setUseGeneratedKeys(true);
        // 允许使用自定义的主键生成器
        configuration.setUseGeneratedKeys(true);
        // 开启驼峰命名转换
        configuration.setMapUnderscoreToCamelCase(true);
        // 设置默认执行器为BATCH
        configuration.setDefaultExecutorType(ExecutorType.BATCH);
        
        factory.setConfiguration(configuration);
        
        return factory.getObject();
    }
}