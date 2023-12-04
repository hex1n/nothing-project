package com.hexin.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author hex1n
 * @Date 2023/12/4/21:30
 * @Description
 **/
@AllArgsConstructor
@Getter
public enum TransactionPropagationEnum {
    /**
     *
     */
    DEFAULT("transactionTemplateDefault", "支持当前事务,如果当前没有事务,就新建一个事务"),
    /**
     *
     */
    REQUIRES_NEW("transactionTemplateNew", "新建事务,如果当前存在事务,把当前事务挂起"),
    ;

    private final String code;
    private final String desc;

}
