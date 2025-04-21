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
public class EntityGenerator {
    private final Configuration configuration;
    private final GeneratorConfig config;

    public EntityGenerator(GeneratorConfig config) {
        this.config = config;
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
    }

    private String convertToCamelCase(String tableName) {
        StringBuilder result = new StringBuilder();
        boolean nextUpper = true;

        for (char c : tableName.toCharArray()) {
            if (c == '_') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    result.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }

    public void generateEntity(TableInfo tableInfo) {
        try {
            Template template = configuration.getTemplate("entity.ftl");
            
            String className = convertToCamelCase(tableInfo.getTableName());
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("package", config.getPackageConfig());
            dataModel.put("className", className);
            dataModel.put("tableName", tableInfo.getTableName());
            dataModel.put("columns", tableInfo.getColumns());
            dataModel.put("strategyConfig", config.getStrategyConfig());

            String outputPath = config.getPackageConfig().getOutputDir() + "/" +
                              config.getPackageConfig().getEntityPackage().replace(".", "/") +
                              "/" + className + ".java";

            File file = new File(outputPath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                boolean dirCreated = parentDir.mkdirs();
                if (!dirCreated) {
                    throw new RuntimeException("Failed to create directory: " + parentDir.getAbsolutePath());
                }
            }

            try (Writer writer = new FileWriter(outputPath)) {
                template.process(dataModel, writer);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate entity", e);
        }
    }
}