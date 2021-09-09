package com.hexin.demo.Test.designmode.simplefactory;

/**
 * @author hex1n
 * @date 2021/1/11 14:09
 * @description
 */
public class Chinese implements Person {
    @Override
    public String sayHello(String name) {
        return name+":你好";
    }

    @Override
    public String sayGoodbye(String name) {
        return name+":再见";
    }
}
