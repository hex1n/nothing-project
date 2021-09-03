package com.bins.springboot.dubbo.consumer.Test.designmode.responsibility;

/**
 * @author hex1n
 * @date 2021/3/31 10:29
 * @description
 */
public class ChainOfResponsibilityPattern {

    public static void main(String[] args) {
        ClassAdviser classAdviser = new ClassAdviser();
        DepartmentHead departmentHead = new DepartmentHead();
        classAdviser.setNext(departmentHead);
        classAdviser.handleRequest(2);
    }
}
