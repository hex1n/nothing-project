package com.hexin.demo.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

@Slf4j
public class TemplateProcessor {
    private final Configuration configuration;
    private static final String TEMPLATE_PATH = "/templates";
    
    public TemplateProcessor() {
        this.configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(this.getClass(), TEMPLATE_PATH);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    
    public void process(String templateName, Object dataModel, String outputPath) {
        try {
            Template template = configuration.getTemplate(templateName);
            File outputFile = new File(outputPath);
            FileUtils.forceMkdirParent(outputFile);
            
            try (Writer writer = new FileWriter(outputFile)) {
                template.process(dataModel, writer);
            }
        } catch (Exception e) {
            log.error("Failed to process template: " + templateName, e);
            throw new RuntimeException(e);
        }
    }
}