package com.hexin.demo.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractGenerator {
    protected final Configuration configuration;
    protected final GeneratorConfig config;
    
    protected AbstractGenerator(GeneratorConfig config) {
        this.config = config;
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
    }
    
    protected void generateFile(String templateName, String outputPath, Map<String, Object> dataModel) {
        try {
            Template template = configuration.getTemplate(templateName);
            File outputFile = new File(outputPath);
            FileUtils.forceMkdirParent(outputFile);
            
            try (Writer writer = new FileWriter(outputFile)) {
                template.process(dataModel, writer);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate file: " + outputPath, e);
        }
    }
    
    protected Map<String, Object> createBaseDataModel(TableInfo tableInfo) {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("className", tableInfo.getClassName());
        dataModel.put("tableName", tableInfo.getTableName());
        dataModel.put("columns", tableInfo.getColumns());
        dataModel.put("primaryKey", tableInfo.getPrimaryKey());
        dataModel.put("comment", tableInfo.getComment());
        return dataModel;
    }
}