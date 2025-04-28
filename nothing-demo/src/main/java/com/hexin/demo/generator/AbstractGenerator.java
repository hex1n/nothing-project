package com.hexin.demo.generator;

import com.hexin.demo.util.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public abstract class AbstractGenerator {
    protected final GeneratorConfig config;
    protected final Configuration configuration;
    protected static final AtomicInteger successCount = new AtomicInteger(0);
    protected static final AtomicInteger failCount = new AtomicInteger(0);

    protected AbstractGenerator(GeneratorConfig config) {
        this.config = config;
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        configuration.setDefaultEncoding("UTF-8");
    }

    /**
     * 渲染模板并生成文件
     *
     * @param templateName 模板名称
     * @param dataModel    数据模型
     * @param outputPath   输出路径
     * @throws IOException       IO异常
     * @throws TemplateException 模板异常
     */
    protected void renderTemplate(String templateName, Map<String, Object> dataModel, String outputPath) throws IOException, TemplateException {
        log.debug("渲染模板: {}, 输出到: {}", templateName, outputPath);

        // 获取模板
        Template template = configuration.getTemplate(templateName, "UTF-8");

        // 创建输出目录
        File outputFile = new File(outputPath);
        if (!outputFile.getParentFile().exists()) {
            boolean created = outputFile.getParentFile().mkdirs();
            if (!created) {
                throw new RuntimeException("无法创建目录: " + outputFile.getParentFile().getAbsolutePath());
            }
        }

        // 渲染模板到文件
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
            template.process(dataModel, writer);
            log.info("成功生成文件: {}", outputPath);
            successCount.incrementAndGet();
        } catch (Exception e) {
            failCount.incrementAndGet();
            log.error("生成文件失败: {}, 原因: {}", outputPath, e.getMessage());
            throw e;
        }
    }

    /**
     * 处理表名，去除前缀
     */
    protected String processTableName(String tableName) {
        if (config.getStrategyConfig() != null && config.getStrategyConfig().getTablePrefix() != null) {
            for (String prefix : config.getStrategyConfig().getTablePrefix()) {
                if (tableName.startsWith(prefix)) {
                    return tableName.substring(prefix.length());
                }
            }
        }
        return tableName;
    }

    /**
     * 确保TableInfo中的className是有效的
     */
    protected void ensureClassNameValid(TableInfo tableInfo) {
        if (StringUtils.isEmpty(tableInfo.getClassName())) {
            String processedTableName = processTableName(tableInfo.getTableName());
            String className = StringUtils.toPascalCase(processedTableName);
            tableInfo.setClassName(className);
            log.info("自动生成类名: {}", className);
        }
    }

    /**
     * 创建基础数据模型
     */
    protected Map<String, Object> createBaseDataModel(TableInfo tableInfo) {
        // 确保className有效
        ensureClassNameValid(tableInfo);

        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("package", config.getPackageConfig());
        dataModel.put("className", tableInfo.getClassName());
        dataModel.put("tableName", tableInfo.getTableName());
        dataModel.put("columns", tableInfo.getColumns());
        dataModel.put("primaryKey", tableInfo.getPrimaryKey());
        dataModel.put("strategyConfig", config.getStrategyConfig());
        dataModel.put("entityPackage", config.getPackageConfig().getEntityPackage());
        dataModel.put("mapperPackage", config.getPackageConfig().getMapperPackage());
        dataModel.put("servicePackage", config.getPackageConfig().getServicePackage());

        return dataModel;
    }

    /**
     * 获取生成统计信息
     */
    public static String getGenerationStatistics() {
        return String.format("代码生成完成，成功: %d, 失败: %d", successCount.get(), failCount.get());
    }
}