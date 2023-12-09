package com.hexin.demo.common;

/**
 * @Author hex1n
 * @Date 2023/12/5/22:47
 * @Description
 **/
public interface BusinessTransactionTemplate {

    void executeTransaction(BizTemplateCallback callbackWithoutResult, TransactionPropagationEnum transactionPropagationEnum);

    <T> T executeTransactionWithResult(BizTemplateCallbackWithResult<T> bizTemplateCallback, TransactionPropagationEnum transactionPropagationEnum);
}
