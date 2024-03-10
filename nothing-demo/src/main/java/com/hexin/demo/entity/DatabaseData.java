package com.hexin.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author hex1n
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatabaseData {
    private String name;
    private Boolean enabled;
    private String offsetPath;
    private String host;
    private String port;
    private String username;
    private String password;
    private String serverId;
    private List<String> table;
    private List<String> column;
    private String historyPath;
}