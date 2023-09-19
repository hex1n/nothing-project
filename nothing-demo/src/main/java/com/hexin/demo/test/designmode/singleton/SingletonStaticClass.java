package com.hexin.demo.test.designmode.singleton;

/**
 * @author hex1n
 * @date 2021/1/11 13:58
 * @description
 */
public class SingletonStaticClass {
    private SingletonStaticClass(){};
    private static class SingletonHolder{
        private static SingletonStaticClass sInstance=new SingletonStaticClass();
    }
    public static SingletonStaticClass getInstance(){
        return SingletonHolder.sInstance;
    }
}
