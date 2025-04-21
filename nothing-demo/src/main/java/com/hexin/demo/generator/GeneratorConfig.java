package com.hexin.demo.generator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorConfig {
    private DataSourceConfig dataSource;
    private PackageConfig packageConfig;
    private TemplateConfig templateConfig;
    private StrategyConfig strategyConfig;

    @Data
    @Builder
    public static class DataSourceConfig {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }

    @Data
    @Builder
    public static class PackageConfig {
        private String basePackage;
        private String entityPackage;
        private String mapperPackage;
        private String servicePackage;
        private String outputDir;
    }

    @Data
    @Builder
    public static class TemplateConfig {
        private String entityTemplate;
        private String mapperTemplate;
        private String xmlTemplate;
        private String serviceTemplate;
        private String serviceImplTemplate;
    }

    @Data
    @Builder
    public static class StrategyConfig {
        private String[] tablePrefix;
        private String[] includeTables;
        private String[] excludeTables;
        private boolean lombokModel;
        private boolean restController;
        private boolean chainModel;
    }
}