package com.hexin.demo.Test.designmode.simplefactory;

/**
 * @author hex1n
 * @date 2021/1/11 14:10
 * @description
 */
public class PersonFactory {
    public Person getPerson(String name) {
        if ("chinese".equals(name)) {
            return new Chinese();
        } else if ("american".equals(name)) {
            return new American();
        } else {
            return null;
        }
    }
}
