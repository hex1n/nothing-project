package com.hexin.demo.generator;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DatabaseMetaReader {
    private final GeneratorConfig config;
    private Connection connection;
    
    public DatabaseMetaReader(GeneratorConfig config) {
        this.config = config;
    }
    
    /**
     * 获取数据库连接
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName(config.getDataSource().getDriverClassName());
                connection = DriverManager.getConnection(
                        config.getDataSource().getUrl(),
                        config.getDataSource().getUsername(),
                        config.getDataSource().getPassword()
                );
            } catch (ClassNotFoundException e) {
                throw new SQLException("找不到数据库驱动: " + e.getMessage(), e);
            }
        }
        return connection;
    }
    
    /**
     * 获取表信息
     */
    public TableInfo getTableInfo(String tableName) throws SQLException {
        log.info("读取表信息: {}", tableName);
        Connection conn = getConnection();
        DatabaseMetaData metaData = conn.getMetaData();
        
        TableInfo.TableInfoBuilder builder = TableInfo.builder().tableName(tableName);
        
        // 获取表注释
        try (ResultSet rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
            if (rs.next()) {
                builder.comment(rs.getString("REMARKS"));
            }
        }
        
        // 获取列信息
        List<ColumnInfo> columns = new ArrayList<>();
        try (ResultSet rs = metaData.getColumns(null, null, tableName, null)) {
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                String dataType = rs.getString("TYPE_NAME");
                int columnSize = rs.getInt("COLUMN_SIZE");
                boolean nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable;
                String comment = rs.getString("REMARKS");
                
                ColumnInfo columnInfo = ColumnInfo.builder()
                        .columnName(columnName)
                        .dataType(dataType)
                        .columnSize(columnSize)
                        .nullable(nullable)
                        .comment(comment)
                        .isPrimaryKey(false) // 默认非主键
                        .build();
                
                columns.add(columnInfo);
            }
        }
        
        builder.columns(columns);
        
        // 识别主键
        try (ResultSet rs = metaData.getPrimaryKeys(null, null, tableName)) {
            while (rs.next()) {
                String pkColumnName = rs.getString("COLUMN_NAME");
                for (ColumnInfo column : columns) {
                    if (column.getColumnName().equals(pkColumnName)) {
                        column.setIsPrimaryKey(true);
                        break;
                    }
                }
            }
        }
        
        return builder.build();
    }
    
    /**
     * 关闭数据库连接
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("关闭数据库连接失败: {}", e.getMessage(), e);
            }
        }
    }
}