package com.hexin.demo.generator;

import com.google.common.collect.ImmutableList;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;

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
                        .outputDir("./generated-code")
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
        testConnection(config);
        generator.generateBatch(ImmutableList.of("t_member_info"));

        System.out.println("代码生成完成！");
    }

    // 在生成代码前添加测试连接的方法
    private static void testConnection(GeneratorConfig config) {
        try {
            Class.forName(config.getDataSource().getDriverClassName());
            Connection conn = DriverManager.getConnection(
                    config.getDataSource().getUrl(),
                    config.getDataSource().getUsername(),
                    config.getDataSource().getPassword()
            );
            System.out.println("数据库连接成功");

            // 测试表是否存在
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "t_member_info", null);
            if (tables.next()) {
                System.out.println("表t_member_info存在");
            } else {
                System.out.println("表t_member_info不存在");
            }

            conn.close();
        } catch (Exception e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
}