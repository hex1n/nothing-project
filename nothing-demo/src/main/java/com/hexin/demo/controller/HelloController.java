package com.hexin.demo.controller;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
public class HelloController {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password;


    @RequestMapping("/hello")
    public String helloWorld() throws IOException {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            log.error("leads scoring healthCheck mysql error:", e);
        }
        return "";
    }


    @Autowired
    Executor commentAsyncA;

    public void test() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }

        List<List<Integer>> subLists = Lists.partition(list, 1000);
        CountDownLatch latch = new CountDownLatch(subLists.size());
        for (List<Integer> subList : subLists) {
            commentAsyncA.execute(() -> {

            });
        }
    }

    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();
        while (count.get() < 5) {
            count.incrementAndGet();
            log.info("============={}",count.get());
        }
    }


}
