package com.hexin.demo.test.designmode.singleton;

/**
 * @author hex1n
 * @date 2021/1/20 10:56
 * @description
 */
public class Singleton {

    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        //判断对象是否已经实例过，没有实例化过才进入加锁代码
        if (instance==null){
            synchronized (Singleton.class){
                if (instance==null){
                    instance=new Singleton();
                }
            }
        }
        return instance;
    }
}
