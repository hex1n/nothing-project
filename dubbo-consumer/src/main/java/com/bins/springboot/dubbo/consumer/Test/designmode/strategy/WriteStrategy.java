package com.bins.springboot.dubbo.consumer.Test.designmode.strategy;

/**
 * @author hex1n
 * @date 2021/1/11 14:16
 * @description
 */
public class WriteStrategy implements Strategy{
    @Override
    public void testStrategy() {
        System.out.println("我要写字");
    }
}
