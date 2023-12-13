package com.hexin.demo.exception;

/**
 * @Author hex1n
 * @Date 2023/12/13/21:51
 * @Description
 **/
public class BizException extends RuntimeException {
    private Integer errorCode;

    public BizException(String message) {
        super(message);
    }

    public BizException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode == null ? 500 : errorCode;
    }
}
