package com.hexin.demo.generator;

public class GeneratorDemo {
    public static void main(String[] args) {
        GeneratorConfig config = GeneratorConfig.builder()
                .dataSource(GeneratorConfig.DataSourceConfig.builder()
                        .url("jdbc:mysql://localhost:3306/test")
                        .username("root")
                        .password("mysql123")
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .build())
                .packageConfig(GeneratorConfig.PackageConfig.builder()
                        .basePackage("com.hexin.demo")
                        .outputDir("src/main/java")
                        .entityPackage("com.hexin.demo.generator.entity")
                        .mapperPackage("com.hexin.demo.generator.mapper")
                        .servicePackage("com.hexin.demo.generator.service")
                        .build())
                .strategyConfig(GeneratorConfig.StrategyConfig.builder()
                        .tablePrefix(new String[]{"t_"})
                        .lombokModel(true)
                        .build())
                .build();

        CodeGenerator generator = new CodeGenerator(config);
        generator.generate("t_photo_info");
    }
}