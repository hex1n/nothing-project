package com.hexin.demo.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ServiceGenerator {
    private final Configuration configuration;
    private final GeneratorConfig config;

    public ServiceGenerator(GeneratorConfig config) {
        this.config = config;
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    public void generateService(com.hexin.demo.generator.TableInfo tableInfo) {
        try {
            // 生成Service接口
            generateServiceInterface(tableInfo);
            // 生成Service实现类
            generateServiceImpl(tableInfo);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate service", e);
        }
    }
    private void generateServiceInterface(TableInfo tableInfo) throws Exception {
        Template template = configuration.getTemplate("service.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", config.getPackageConfig().getServicePackage());
        dataModel.put("entityPackage", config.getPackageConfig().getEntityPackage());
        dataModel.put("className", tableInfo.getClassName());
        dataModel.put("tableName", tableInfo.getTableName());
        dataModel.put("columns", tableInfo.getColumns());

        String outputPath = config.getPackageConfig().getOutputDir() + "/" +
                config.getPackageConfig().getServicePackage().replace(".", "/") +
                "/" + tableInfo.getClassName() + "Service.java";

        File outputFile = new File(outputPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        try (Writer writer = new FileWriter(outputFile)) {
            template.process(dataModel, writer);
        }
    }
    private void generateServiceImpl(TableInfo tableInfo) throws Exception {
        Template template = configuration.getTemplate("serviceImpl.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", config.getPackageConfig().getBasePackage() + ".impl");
        dataModel.put("entityPackage", config.getPackageConfig().getEntityPackage());
        dataModel.put("mapperPackage", config.getPackageConfig().getMapperPackage());
        dataModel.put("servicePackage", config.getPackageConfig().getServicePackage());
        dataModel.put("className", tableInfo.getClassName());
        dataModel.put("tableName", tableInfo.getTableName());
        dataModel.put("columns", tableInfo.getColumns());

        String outputPath = config.getPackageConfig().getOutputDir() + "/" +
                config.getPackageConfig().getServicePackage().replace(".", "/") +
                "/impl/" + tableInfo.getClassName() + "ServiceImpl.java";

        File outputFile = new File(outputPath);
        if (!outputFile.getParentFile().exists()) {
            outputFile.getParentFile().mkdirs();
        }

        try (Writer writer = new FileWriter(outputFile)) {
            template.process(dataModel, writer);
        }
    }
}