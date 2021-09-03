package com.bins.springboot.dubbo.consumer.Test.designmode.strategy;

/**
 * @author hex1n
 * @date 2021/1/11 14:17
 * @description
 */
public class StrategyClient {
    public static void main(String[] args) {
        PrintStrategy printStrategy = new PrintStrategy();
        WriteStrategy writeStrategy = new WriteStrategy();
        Context contextA = new Context(printStrategy);
        Context contextB = new Context(writeStrategy);
        contextA.doAction();
        contextB.doAction();
    }
}
