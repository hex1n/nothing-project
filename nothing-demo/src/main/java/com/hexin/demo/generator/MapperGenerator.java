package com.hexin.demo.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class MapperGenerator extends AbstractGenerator {
    
    public MapperGenerator(GeneratorConfig config) {
        super(config);
    }
    
    /**
     * 生成Mapper接口和XML
     * @param tableInfo 表信息
     */
    public void generateMapper(TableInfo tableInfo) {
        try {
            log.info("开始生成Mapper: {}", tableInfo.getTableName());
            
            // 生成Mapper接口
            generateMapperInterface(tableInfo);
            
            // 生成Mapper XML
            generateMapperXml(tableInfo);
            
            log.info("Mapper生成完成: {}", tableInfo.getClassName());
        } catch (Exception e) {
            log.error("生成Mapper失败: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate mapper: " + tableInfo.getClassName(), e);
        }
    }
    
    /**
     * 生成Mapper接口
     */
    private void generateMapperInterface(TableInfo tableInfo) throws IOException, TemplateException {
        // 创建数据模型
        Map<String, Object> dataModel = createBaseDataModel(tableInfo);
        
        // 生成输出路径
        String outputPath = config.getPackageConfig().getOutputDir() + "/" +
            config.getPackageConfig().getMapperPackage().replace(".", "/") +
            "/" + tableInfo.getClassName() + "Mapper.java";
        
        // 渲染模板
        renderTemplate("mapper.ftl", dataModel, outputPath);
    }
    
    /**
     * 生成Mapper XML
     */
    private void generateMapperXml(TableInfo tableInfo) throws IOException, TemplateException {
        // 创建数据模型
        Map<String, Object> dataModel = createBaseDataModel(tableInfo);
        
        // 创建输出目录
        String outputDir = "src/main/resources/mapper/";
        
        // 生成输出路径
        String outputPath = outputDir + tableInfo.getClassName() + "Mapper.xml";
        
        // 渲染模板
        renderTemplate("mapper.xml.ftl", dataModel, outputPath);
    }
}