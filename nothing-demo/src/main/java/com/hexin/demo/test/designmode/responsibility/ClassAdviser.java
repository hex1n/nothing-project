package com.hexin.demo.test.designmode.responsibility;

/**
 * @author hex1n
 * @date 2021/3/31 10:34
 * @description
 */
public class ClassAdviser extends Leader {

    @Override
    public void handleRequest(int leaveDays) {
        if (leaveDays <= 2) {
            System.out.println("班主任批准您请假" + leaveDays + "天");
        } else {
            if (getNext() != null) {
                getNext().handleRequest(leaveDays);
            } else {
                System.out.println("请假天数太多，没有人批准该假");
            }
        }
    }
}
