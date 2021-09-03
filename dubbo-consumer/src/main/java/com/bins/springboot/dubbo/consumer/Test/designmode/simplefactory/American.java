package com.bins.springboot.dubbo.consumer.Test.designmode.simplefactory;

/**
 * @author hex1n
 * @date 2021/1/11 14:10
 * @description
 */
public class American implements Person{
    @Override
    public String sayHello(String name) {
        return name+":hello";
    }

    @Override
    public String sayGoodbye(String name) {
        return name+"goodbye";
    }
}
