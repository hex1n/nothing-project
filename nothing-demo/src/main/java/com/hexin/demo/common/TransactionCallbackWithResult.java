package com.hexin.demo.common;

/**
 * @Author hex1n
 * @Date 2023/12/3/21:19
 * @Description
 **/
@FunctionalInterface
public interface TransactionCallbackWithResult<T> {

    <T> T executeTransactionWithResult();

}
