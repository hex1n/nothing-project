package com.hexin.demo.util;

public class StringUtils {
    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * 转换为帕斯卡命名（首字母大写的驼峰命名）
     */
    public static String toPascalCase(String input) {
        if (isEmpty(input)) {
            return input;
        }
        
        StringBuilder result = new StringBuilder();
        boolean nextUpper = true;
        
        for (char c : input.toCharArray()) {
            if (c == '_' || c == ' ' || c == '-' || !Character.isLetterOrDigit(c)) {
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
    
    /**
     * 转换为驼峰命名（首字母小写）
     */
    public static String toCamelCase(String input) {
        String pascal = toPascalCase(input);
        if (isEmpty(pascal) || pascal.length() <= 1) {
            return pascal != null ? pascal.toLowerCase() : pascal;
        }
        return Character.toLowerCase(pascal.charAt(0)) + pascal.substring(1);
    }
}