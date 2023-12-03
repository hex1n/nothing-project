package com.hexin.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;

/**
 * @Author hex1n
 * @Date 2023/12/3/21:15
 * @Description
 **/
@Component
@Slf4j
public class BusinessTransactionTemplate {

    @Resource
    private TransactionTemplate transactionTemplate;

    public void executeTransaction(TransactionCallbackWithoutResult callbackWithoutResult, Propagation propagationBehavior) {
        transactionTemplate.setPropagationBehavior(propagationBehavior.value());
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus) {
                try {
                    callbackWithoutResult.executeTransactionWithoutResult();
                } catch (Exception e) {
                    log.error("事务处理失败",e);
                    transactionStatus.setRollbackOnly();
                }
                return null;
            }
        });

    }

    public T executeTransactionWithResult(TransactionCallbackWithResult<T> callbackWithResult, Propagation propagationBehavior) {
        transactionTemplate.setPropagationBehavior(propagationBehavior.value());
        transactionTemplate.execute(new TransactionCallback<T>() {
            @Override
            public T doInTransaction(TransactionStatus transactionStatus) {
                try {
                    T result = callbackWithResult.executeTransactionWithResult();
                    return result;
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                }
                return null;
            }
        });
        return null;
    }

}
