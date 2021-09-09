package com.hexin.demo.Test.designmode.strategy;

/**
 * @author hex1n
 * @date 2021/1/11 14:14
 * @description
 */
public class Context {
    private Strategy stg;

    public Context(Strategy theStg) {
        this.stg = theStg;
    }

    public void doAction() {
        this.stg.testStrategy();
    }
}
