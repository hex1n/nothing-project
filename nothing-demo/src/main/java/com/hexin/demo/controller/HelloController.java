package com.hexin.springboot.dubbo.consumer.controller;

import com.hexin.springboot.dubbo.consumer.Test.WriteText;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {

	/*@DubboReference
	private UserApi userApi;*/

    @RequestMapping("/hello")
    public String helloWorld() throws IOException {
        String fileName = "\temp\tenant.txt";
        WriteText.writeToText("", "", fileName);
        return null;
    }

}
