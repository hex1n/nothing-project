package com.hexin.demo.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class EntityGenerator extends AbstractGenerator {
    
    public EntityGenerator(GeneratorConfig config) {
        super(config);
    }
    
    /**
     * 生成实体类
     * @param tableInfo 表信息
     */
    public void generateEntity(TableInfo tableInfo) {
        try {
            log.info("开始生成实体类: {}", tableInfo.getTableName());
            
            // 创建数据模型
            Map<String, Object> dataModel = createBaseDataModel(tableInfo);
            
            // 生成输出路径
            String outputPath = config.getPackageConfig().getOutputDir() + "/" +
                config.getPackageConfig().getEntityPackage().replace(".", "/") +
                "/" + tableInfo.getClassName() + ".java";
            
            // 渲染模板
            renderTemplate("entity.ftl", dataModel, outputPath);
            
            log.info("实体类生成完成: {}", tableInfo.getClassName());
        } catch (IOException | TemplateException e) {
            log.error("生成实体类失败: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate entity: " + tableInfo.getClassName(), e);
        }
    }
}