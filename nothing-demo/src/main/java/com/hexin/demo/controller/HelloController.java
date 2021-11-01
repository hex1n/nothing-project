package com.hexin.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@Slf4j
public class HelloController {

    @Value("${config.longValue}")
    private String value;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;


    @RequestMapping("/hello")
    public String helloWorld() throws IOException {
        try{
          Connection  connection = DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            log.error("leads scoring healthCheck mysql error:",e);
        }
        System.out.println(value);
        return value+"";
    }

}
