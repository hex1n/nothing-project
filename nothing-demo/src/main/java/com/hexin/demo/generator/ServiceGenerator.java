package com.hexin.demo.generator;

import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class ServiceGenerator extends AbstractGenerator {
    
    public ServiceGenerator(GeneratorConfig config) {
        super(config);
    }
    
    /**
     * 生成Service接口和实现类
     * @param tableInfo 表信息
     */
    public void generateService(TableInfo tableInfo) {
        try {
            log.info("开始生成Service: {}", tableInfo.getTableName());
            
            // 生成Service接口
            generateServiceInterface(tableInfo);
            
            // 生成Service实现类
            generateServiceImpl(tableInfo);
            
            log.info("Service生成完成: {}", tableInfo.getClassName());
        } catch (Exception e) {
            log.error("生成Service失败: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate service: " + tableInfo.getClassName(), e);
        }
    }
    
    /**
     * 生成Service接口
     */
    private void generateServiceInterface(TableInfo tableInfo) throws IOException, TemplateException {
        // 创建数据模型
        Map<String, Object> dataModel = createBaseDataModel(tableInfo);
        
        // 生成输出路径
        String outputPath = config.getPackageConfig().getOutputDir() + "/" +
            config.getPackageConfig().getServicePackage().replace(".", "/") +
            "/" + tableInfo.getClassName() + "Service.java";
        
        // 渲染模板
        renderTemplate("service.ftl", dataModel, outputPath);
    }
    
    /**
     * 生成Service实现类
     */
    private void generateServiceImpl(TableInfo tableInfo) throws IOException, TemplateException {
        // 创建数据模型
        Map<String, Object> dataModel = createBaseDataModel(tableInfo);
        dataModel.put("implPackage", config.getPackageConfig().getServicePackage() + ".impl");
        
        // 生成输出路径
        String outputPath = config.getPackageConfig().getOutputDir() + "/" +
            config.getPackageConfig().getServicePackage().replace(".", "/") +
            "/impl/" + tableInfo.getClassName() + "ServiceImpl.java";
        
        // 渲染模板
        renderTemplate("service.impl.ftl", dataModel, outputPath);
    }
}