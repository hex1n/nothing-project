package com.hexin.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {

    @Value("${config.longValue}")
    private String value;

    @RequestMapping("/hello")
    public String helloWorld() throws IOException {
        System.out.println(value);
        return value+"";
    }

}
