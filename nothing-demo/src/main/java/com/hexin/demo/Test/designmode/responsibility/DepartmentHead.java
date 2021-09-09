package com.hexin.demo.Test.designmode.responsibility;

/**
 * @author hex1n
 * @date 2021/3/31 10:37
 * @description
 */
public class DepartmentHead extends Leader {

    @Override
    public void handleRequest(int leaveDays) {
        if (leaveDays<=7){
            System.out.println("系主任批准您请假" + leaveDays + "天");
        }else {
            if (getNext()!=null){
                getNext().handleRequest(leaveDays);
            }else {
                System.out.println("请假天数太多，没有人批准该假");
            }
        }
    }
}
