package com.hexin.demo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author hex1n
 * @Date 2023/12/9/14:59
 * @Description
 **/
public class WebResponse<T> implements Serializable {
    private static final long serialVersionUID = 7514893566045576596L;
    private static final int SUCCESS_CODE = 0;
    private static final int FAIL_CODE = -1;
    private Integer code;
    private String msg;
    private Boolean success;
    private transient String timestamp;
    private T data;

    public WebResponse(Integer code, Boolean success, String msg, T data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public WebResponse<T> withCode(int code) {
        this.code = code;
        return this;
    }

    public WebResponse<T> withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public WebResponse<T> withMessage(String message) {
        this.msg = message;
        return this;
    }

    public WebResponse<T> withData(T data) {
        this.data = data;
        return this;
    }

    private static final String SUCCESS_MSG = "success";

    public static WebResponse getSuccessResult() {
        return new WebResponse(SUCCESS_CODE, Boolean.TRUE, SUCCESS_MSG, null);
    }

    public static WebResponse getFailResult() {
        return new WebResponse(FAIL_CODE, Boolean.FALSE, "", null);
    }

    public static WebResponse getFailResult(String msg) {
        return new WebResponse(FAIL_CODE, Boolean.FALSE, msg, null);
    }


    public static <T> WebResponse<T> buildSuccessWithoutData() {
        return new WebResponse<T>(SUCCESS_CODE, Boolean.TRUE, SUCCESS_MSG, null);
    }

    public static <T> WebResponse<T> buildFailResult(int code, String msg) {
        return new WebResponse<T>(code, Boolean.FALSE, msg, null);
    }

    public static <T> WebResponse<T> buildSuccessWithData(T data) {
        return new WebResponse<>(SUCCESS_CODE, Boolean.TRUE, SUCCESS_MSG, data);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTimestamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    @Override
    public String toString() {
        if (this.data == null) {
            return String.format("{\"code\": %d, \"msg\":\"%s\"}", this.code, this.msg);
        }
        return String.format("{\"code\": %d, \"msg\":\"%s\", data: %s}", this.code, this.msg, data);
    }

}
