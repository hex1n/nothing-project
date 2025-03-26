package com.hexin.demo.test.designmode.responsibility;

import lombok.Getter;
import lombok.Setter;

/**
 * @author hex1n
 * @date 2021/3/31 10:30
 * @description
 */
@Getter
@Setter
public abstract class Leader {
    private Leader next;
    public abstract void handleRequest(int leaveDays);

}
