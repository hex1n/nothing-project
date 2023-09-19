package com.hexin.demo.test.designmode.strategy;

/**
 * @author hex1n
 * @date 2021/1/11 14:16
 * @description
 */
public class PrintStrategy implements Strategy {
    @Override
    public void testStrategy() {
        System.out.println("我要打印");
    }
}
