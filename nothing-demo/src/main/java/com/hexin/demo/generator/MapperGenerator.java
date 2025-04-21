package com.hexin.demo.generator;

import com.hexin.demo.util.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MapperGenerator {
    private final Configuration configuration;
    private final GeneratorConfig config;

    public MapperGenerator(GeneratorConfig config) {
        this.config = config;
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    public void generateMapper(TableInfo tableInfo) {
        try {
            log.info("开始生成Mapper: {}", tableInfo.getTableName());
            
            // 确保className存在
            ensureClassNameExists(tableInfo);
            
            // 生成Mapper接口
            generateMapperInterface(tableInfo);
            // 生成Mapper XML
            generateMapperXml(tableInfo);
            
            log.info("完成生成Mapper: {}", tableInfo.getTableName());
        } catch (Exception e) {
            log.error("生成Mapper失败: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate mapper", e);
        }
    }
    
    /**
     * 确保tableInfo中的className存在，如果不存在则生成一个
     */
    private void ensureClassNameExists(TableInfo tableInfo) {
        if (StringUtils.isEmpty(tableInfo.getClassName())) {
            log.warn("TableInfo中的className为空，将基于表名生成");
            String processedTableName = processTableName(tableInfo.getTableName());
            String className = StringUtils.toPascalCase(processedTableName);
            tableInfo.setClassName(className);
            log.info("生成的className: {}", className);
        }
    }
    
    /**
     * 处理表名，去除前缀
     */
    private String processTableName(String tableName) {
        if (config.getStrategyConfig() != null && 
            config.getStrategyConfig().getTablePrefix() != null) {
            
            for (String prefix : config.getStrategyConfig().getTablePrefix()) {
                if (tableName.startsWith(prefix)) {
                    return tableName.substring(prefix.length());
                }
            }
        }
        return tableName;
    }

    private void generateMapperInterface(TableInfo tableInfo) throws Exception {
        Template template = configuration.getTemplate("mapper.ftl");

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", config.getPackageConfig().getMapperPackage());
        dataModel.put("entityPackage", config.getPackageConfig().getEntityPackage());
        dataModel.put("className", tableInfo.getClassName());
        dataModel.put("tableName", tableInfo.getTableName());
        dataModel.put("columns", tableInfo.getColumns());

        String outputPath = config.getPackageConfig().getOutputDir() + "/" +
                config.getPackageConfig().getMapperPackage().replace(".", "/") +
                "/" + tableInfo.getClassName() + "Mapper.java";

        File outputFile = new File(outputPath);
        if (!outputFile.getParentFile().exists()) {
            boolean created = outputFile.getParentFile().mkdirs();
            if (!created) {
                throw new RuntimeException("无法创建目录: " + outputFile.getParentFile().getAbsolutePath());
            }
        }

        try (Writer writer = new FileWriter(outputFile)) {
            template.process(dataModel, writer);
            log.info("成功生成Mapper接口: {}", outputPath);
        }
    }

    private void generateMapperXml(TableInfo tableInfo) throws Exception {
        try {
            log.info("开始生成Mapper XML文件: {}", tableInfo.getClassName());
            
            // 设置明确的模板编码
            configuration.setDefaultEncoding("UTF-8");
            configuration.setOutputEncoding("UTF-8");
            
            Template template = configuration.getTemplate("mapper.xml.ftl", "UTF-8");
            
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("mapperPackage", config.getPackageConfig().getMapperPackage());
            dataModel.put("entityPackage", config.getPackageConfig().getEntityPackage());
            dataModel.put("className", tableInfo.getClassName());
            dataModel.put("tableName", tableInfo.getTableName());
            dataModel.put("columns", tableInfo.getColumns());
            dataModel.put("primaryKey", tableInfo.getPrimaryKey());
            
            // 创建输出目录
            String outputDir = config.getPackageConfig().getOutputDir() + "/resources/mapper/";
            File dir = new File(outputDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    throw new RuntimeException("无法创建目录: " + outputDir);
                }
            }
            
            // 使用tableInfo.getClassName()来构建文件名
            String outputPath = outputDir + tableInfo.getClassName() + "Mapper.xml";
            File outputFile = new File(outputPath);
            
            // 使用显式的编码设置
            try (Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
                template.process(dataModel, writer);
                log.info("成功生成Mapper XML文件: {}", outputPath);
            }
        } catch (Exception e) {
            log.error("生成Mapper XML时发生错误: {}", e.getMessage(), e);
            throw e;
        }
    }
}