package com.hexin.demo.generator;

import com.google.common.collect.ImmutableList;

public class GeneratorDemo {
    public static void main(String[] args) {
        // 配置代码生成器
        GeneratorConfig config = GeneratorConfig.builder()
                .dataSource(GeneratorConfig.DataSourceConfig.builder()
                        .url("jdbc:mysql://localhost:3306/test?serverTimezone=UTC")
                        .username("root")
                        .password("mysql123")
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .build())
                .packageConfig(GeneratorConfig.PackageConfig.builder()
                        .basePackage("com.hexin.demo")
                        .outputDir("src/main/java")
                        .entityPackage("com.hexin.demo.entity")
                        .mapperPackage("com.hexin.demo.mapper")
                        .servicePackage("com.hexin.demo.service")
                        .build())
                .strategyConfig(GeneratorConfig.StrategyConfig.builder()
                        .tablePrefix(new String[]{"t_"})
                        .lombokModel(true)
                        .build())
                .build();

        // 创建代码生成器并生成代码
        CodeGenerator generator = new CodeGenerator(config);
//        generator.generate("t_user");
        generator.generateBatch(ImmutableList.of("t_user"));

        System.out.println("代码生成完成！");
    }
}