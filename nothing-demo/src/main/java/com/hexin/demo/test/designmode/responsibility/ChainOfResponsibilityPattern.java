package com.hexin.demo.test.designmode.responsibility;

/**
 * @author hex1n
 * @date 2021/3/31 10:29
 * @description
 */
public class ChainOfResponsibilityPattern {

    public static void main(String[] args) {
       com.hexin.demo.test.designmode.responsibility.ClassAdviser classAdviser = new ClassAdviser();
       com.hexin.demo.test.designmode.responsibility.DepartmentHead departmentHead = new DepartmentHead();
        classAdviser.setNext(departmentHead);
        classAdviser.handleRequest(2);
    }
}
