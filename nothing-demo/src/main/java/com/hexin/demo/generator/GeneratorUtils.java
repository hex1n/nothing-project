package com.hexin.demo.generator;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratorUtils {
    private static final Pattern UNDERLINE_PATTERN = Pattern.compile("_([a-z])");
    
    public static String toCamelCase(String input) {
        if (StringUtils.isEmpty(input)) {
            return input;
        }
        
        Matcher matcher = UNDERLINE_PATTERN.matcher(input.toLowerCase());
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    public static String toPascalCase(String input) {
        String camelCase = toCamelCase(input);
        return StringUtils.capitalize(camelCase);
    }
    
    public static String getPackagePath(String packageName) {
        return packageName.replace(".", "/");
    }

}