package com.bins.springboot.dubbo.consumer.Test.designmode.singleton;

/**
 * @author hex1n
 * @date 2021/1/11 11:37
 * @description
 */
public class SingletonTest {
    private static volatile  SingletonTest instance=null;
    private SingletonTest(){};
    public SingletonTest getInstance(){
        if (instance==null){
            synchronized (this){
                if (instance==null){
                    instance=new SingletonTest();
                }
            }
        }
        return instance;
    }

    public SingletonTest getInstance_01(){
        if (instance==null){
            synchronized (this){
                if (instance==null){
                    instance=new SingletonTest();
                }
            }
        }
        return instance;
    }
}
