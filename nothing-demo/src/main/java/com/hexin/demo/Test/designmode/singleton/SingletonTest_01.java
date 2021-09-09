package com.hexin.springboot.dubbo.consumer.Test.designmode.singleton;

/**
 * @author hex1n
 * @date 2021/2/22 19:36
 * @description
 */
public class SingletonTest_01 {

    private volatile static SingletonTest_01 instance;

    private SingletonTest_01(){

    };
    public SingletonTest_01 getInstance(){
        if (instance==null){
            synchronized (this){
                if (instance==null){
                    instance=new SingletonTest_01();
                }
            }
        }
        return instance;
    }
}
