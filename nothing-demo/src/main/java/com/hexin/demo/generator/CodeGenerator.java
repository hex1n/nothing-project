package com.hexin.demo.generator;


import com.hexin.demo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 代码生成器核心类，负责协调各个生成器完成代码生成
 */
@Slf4j
public class CodeGenerator extends AbstractGenerator {
    // 缓存已经处理过的表信息，避免重复处理
    private final Map<String, TableInfo> tableInfoCache = new ConcurrentHashMap<>();

    private final EntityGenerator entityGenerator;
    private final MapperGenerator mapperGenerator;
    private final ServiceGenerator serviceGenerator;

    private static final Executor generatorExecutor = Executors.newFixedThreadPool(
            Math.max(2, Runtime.getRuntime().availableProcessors() / 2)
    );

    public CodeGenerator(GeneratorConfig config) {
        super(config);
        this.entityGenerator = new EntityGenerator(config);
        this.mapperGenerator = new MapperGenerator(config);
        this.serviceGenerator = new ServiceGenerator(config);
    }

    /**
     * 生成单个表的代码
     *
     * @param tableName 表名
     */
    public void generate(String tableName) {
        try {
            log.info("开始生成表 {} 的代码", tableName);
            
            // 获取表信息
            TableInfo tableInfo = getTableInfo(tableName);
            log.info("成功获取表信息：{}", tableInfo);
            
            // 并行生成各类代码
            CompletableFuture<Void> entityFuture = CompletableFuture.runAsync(
                    () -> entityGenerator.generateEntity(tableInfo), generatorExecutor);

            CompletableFuture<Void> mapperFuture = CompletableFuture.runAsync(
                    () -> mapperGenerator.generateMapper(tableInfo), generatorExecutor);

            CompletableFuture<Void> serviceFuture = CompletableFuture.runAsync(
                    () -> serviceGenerator.generateService(tableInfo), generatorExecutor);

            // 等待所有生成任务完成
            CompletableFuture.allOf(entityFuture, mapperFuture, serviceFuture).join();

            log.info("表 {} 的代码生成完成", tableName);
        } catch (Exception e) {
            log.error("生成表 {} 的代码失败: {}，详细堆栈信息：", tableName, e.getMessage(), e);
            throw new CodeGenerationException("Failed to generate code for table: " + tableName, e);
        }
    }

    /**
     * 批量生成多个表的代码
     *
     * @param tableNames 表名列表
     */
    public void generateBatch(List<String> tableNames) {
        if (tableNames == null || tableNames.isEmpty()) {
            log.warn("表名列表为空，未执行代码生成");
            return;
        }

        log.info("开始批量生成 {} 个表的代码", tableNames.size());

        // 并行处理多个表
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String tableName : tableNames) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(
                    () -> generate(tableName), generatorExecutor);
            futures.add(future);
        }

        // 等待所有表处理完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        log.info("批量生成 {} 个表的代码完成", tableNames.size());
    }

    /**
     * 获取表信息，优先从缓存获取
     *
     * @param tableName 表名
     * @return 表信息对象
     */
    private TableInfo getTableInfo(String tableName) {
        return tableInfoCache.computeIfAbsent(tableName, this::buildTableInfo);
    }

    /**
     * 构建并增强表信息
     *
     * @param tableName 表名
     * @return 增强后的表信息
     */
    private TableInfo buildTableInfo(String tableName) {
        log.debug("构建表 {} 的信息", tableName);

        DatabaseMetaReader metaReader = null;
        try {
            // 使用DatabaseMetaReader获取基本表信息
            metaReader = new DatabaseMetaReader(config);
            TableInfo baseTableInfo = metaReader.getTableInfo(tableName);

            // 如果基本表信息已经完整，直接返回
            if (baseTableInfo.getColumns() != null &&
                    baseTableInfo.getColumns().stream().allMatch(column -> column.getJavaType() != null)) {
                return enhanceTableInfo(baseTableInfo);
            }

            // 否则，补充表信息
            List<ColumnInfo> enhancedColumns = new ArrayList<>();

            for (ColumnInfo column : baseTableInfo.getColumns()) {
                // 处理属性名（驼峰命名）
                String propertyName = StringUtils.toCamelCase(column.getColumnName());

                // 处理Java类型
                String javaType = TypeConversionUtils.getJavaType(column.getDataType());

                // 创建增强的列信息
                ColumnInfo enhancedColumn = ColumnInfo.builder()
                        .columnName(column.getColumnName())
                        .propertyName(propertyName)
                        .dataType(column.getDataType())
                        .javaType(javaType)
                        .jdbcType(TypeConversionUtils.getJdbcType(column.getDataType()))  // 设置JDBC类型
                        .columnSize(column.getColumnSize())
                        .nullable(column.getNullable())
                        .comment(column.getComment())
                        .isPrimaryKey(column.getIsPrimaryKey())
                        .build();

                enhancedColumns.add(enhancedColumn);
            }

            // 查找主键
            Set<String> primaryKeyColumns = new HashSet<>();
            try (ResultSet primaryKeyRs = metaReader.getConnection().getMetaData().getPrimaryKeys(null, null, tableName)) {
                while (primaryKeyRs.next()) {
                    primaryKeyColumns.add(primaryKeyRs.getString("COLUMN_NAME"));
                }
            }

            // 标记主键列
            for (ColumnInfo column : enhancedColumns) {
                if (primaryKeyColumns.contains(column.getColumnName())) {
                    column.setIsPrimaryKey(true);
                }
            }

            // 处理表名，去除前缀
            String processedTableName = processTableName(tableName);
            log.debug("处理后的表名: {}", processedTableName);

            // 构建类名
            String className = StringUtils.toPascalCase(processedTableName);
            log.debug("生成的类名: {}", className);

            // 构建增强的表信息
            TableInfo enhancedTableInfo = TableInfo.builder()
                    .tableName(baseTableInfo.getTableName())
                    .className(className)  // 使用新生成的类名
                    .comment(baseTableInfo.getComment())
                    .columns(enhancedColumns)
                    .build();

            return enhanceTableInfo(enhancedTableInfo);
        } catch (SQLException e) {
            log.error("获取表 {} 的信息失败: {}", tableName, e.getMessage(), e);
            throw new CodeGenerationException("Failed to get table info for: " + tableName, e);
        }
    }

    /**
     * 处理表名，去除前缀
     *
     * @param tableName 原始表名
     * @return 处理后的表名
     */
    protected String processTableName(String tableName) {
        if (config.getStrategyConfig() != null &&
                config.getStrategyConfig().getTablePrefix() != null) {

            for (String prefix : config.getStrategyConfig().getTablePrefix()) {
                if (tableName.startsWith(prefix)) {
                    return tableName.substring(prefix.length());
                }
            }
        }
        return tableName;
    }

    /**
     * 增强表信息，添加附加信息
     *
     * @param tableInfo 基本表信息
     * @return 增强后的表信息
     */
    private TableInfo enhanceTableInfo(TableInfo tableInfo) {
        // 设置主键
        ColumnInfo primaryKey = tableInfo.getColumns().stream()
                .filter(ColumnInfo::getIsPrimaryKey)
                .findFirst()
                .orElse(tableInfo.getColumns().isEmpty() ? null : tableInfo.getColumns().get(0));

        if (primaryKey != null && !primaryKey.getIsPrimaryKey()) {
            primaryKey.setIsPrimaryKey(true);
        }

        tableInfo.setPrimaryKey(primaryKey);

        // 处理导入需要的包
        Map<String, String> importMap = new HashMap<>();
        tableInfo.getColumns().forEach(column -> {
            String javaType = column.getJavaType();
            if (javaType != null && javaType.contains(".")) {
                importMap.put(javaType, javaType);
            }
        });

        List<String> imports = new ArrayList<>(importMap.values());
        tableInfo.setImports(imports);

        return tableInfo;
    }

    /**
     * 代码生成异常类
     */
    public static class CodeGenerationException extends RuntimeException {
        public CodeGenerationException(String message) {
            super(message);
        }

        public CodeGenerationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}