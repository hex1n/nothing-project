package com.hexin.demo.controller;

import com.hexin.demo.Test.WriteText;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {


    @RequestMapping("/hello")
    public String helloWorld() throws IOException {
        String fileName = "\temp\tenant.txt";
        WriteText.writeToText("", "", fileName);
        return null;
    }

}
