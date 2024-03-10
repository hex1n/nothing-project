package com.hexin.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author hex1n
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message implements Serializable {
    private Map<String, Object> data;
    private String dbType;
    private String handleType;
    private String database;
    private String table;
}