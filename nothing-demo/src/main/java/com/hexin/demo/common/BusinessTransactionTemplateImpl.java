package com.hexin.demo.common;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author hex1n
 * @Date 2023/12/3/21:15
 * @Description
 **/
@Component
@Slf4j
public class BusinessTransactionTemplateImpl implements BusinessTransactionTemplate {

    @Resource
    private Map<String, TransactionTemplate> transactionTemplateMap = Maps.newConcurrentMap();

    public void executeTransaction(BizTemplateCallback callbackWithoutResult, TransactionPropagationEnum transactionPropagationEnum) {

        TransactionTemplate transactionTemplate = transactionTemplateMap.get(transactionPropagationEnum.getCode());
        if (transactionTemplate == null) {
            log.error("未匹配到对应事务模版");
            return;
        }
        transactionTemplate.executeWithoutResult((transactionStatus) -> {
            try {
                callbackWithoutResult.process();
            } catch (Exception e) {
                log.error("事务处理失败", e);
                transactionStatus.setRollbackOnly();
            }
        });

    }

    public <T> T executeTransactionWithResult(BizTemplateCallbackWithResult<T> bizTemplateCallback, TransactionPropagationEnum transactionPropagationEnum) {
        TransactionTemplate transactionTemplate = transactionTemplateMap.get(transactionPropagationEnum.getCode());
        if (transactionTemplate == null) {
            log.error("未匹配到事务模版");
            return null;
        }

        return transactionTemplate.execute(new TransactionCallback<T>() {
            T processResult;

            @Override
            public T doInTransaction(TransactionStatus transactionStatus) {
                try {
                    processResult = bizTemplateCallback.processWithResult();
                } catch (Exception e) {
                    log.error("事务处理失败", e);
                    transactionStatus.setRollbackOnly();
                }
                return processResult;
            }
        });
    }

}
