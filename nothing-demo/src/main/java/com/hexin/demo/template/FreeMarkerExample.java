package com.hexin.demo.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerExample {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);

    // 静态代码块只初始化一次
    static {
        try {
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER); // 异常处理
            cfg.setDefaultEncoding("UTF-8");
        } catch (Exception e) {
            // 对配置异常进行处理
            throw new RuntimeException("FreeMarker Configuration initialization failed", e);
        }
    }

    public static void main(String[] args) {
        try {
            // 定义模板变量
            Map<String, Object> variables = new HashMap<>();
            variables.put("theme", "dark");

            // 创建 properties 的数据
            Map<String, String> properties = new HashMap<>();
            properties.put("color", "red");
            properties.put("background-color", "blue");

            variables.put("properties", properties);

            // 定义模板字符串
            String themeTemplate = "[data-theme='${theme}'] {\n" +
                                   "<#list properties as key, value>\n" +
                                   "    ${key}: ${value};\n" +
                                   "</#list>\n" +
                                   "}";

            // 渲染模板
            String renderedText = renderTemplate(themeTemplate, variables);

            // 输出渲染结果
            System.out.println(renderedText);

        } catch (IOException | TemplateException e) {
            // 处理渲染异常
            System.err.println("Error processing template: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 渲染模板字符串
     * @param templateString 模板字符串
     * @param variables 传递给模板的变量
     * @return 渲染后的文本
     * @throws IOException
     * @throws TemplateException
     */
    public static String renderTemplate(String templateString, Map<String, Object> variables) throws IOException, TemplateException {
        // 使用字符串模板创建模板对象
        Template template = new Template("themeTemplate", templateString, cfg);

        StringWriter writer = new StringWriter();
        template.process(variables, writer); // 渲染模板
        return writer.toString();
    }
}