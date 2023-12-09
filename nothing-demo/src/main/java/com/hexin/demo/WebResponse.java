package com.hexin.demo;

import com.hexin.demo.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author hex1n
 * @Date 2023/12/9/14:59
 * @Description
 **/
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse<T> {
    private int statusCode;
    private String message;
    private boolean success;
    private T data;

    public static <T> WebResponse<Void> success() {
        return new WebResponse<Void>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), Boolean.TRUE, null);
    }

    public static <T> WebResponse<T> success(T data) {
        return new WebResponse<>(ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), Boolean.TRUE, data);
    }

    public static <T> WebResponse<T> error(ErrorCode errorCode) {
        return new WebResponse(errorCode.getCode(), errorCode.getMessage(), Boolean.FALSE, null);
    }

    public static <T> WebResponse<T> error(ErrorCode errorCode, String message) {
        return new WebResponse(errorCode.getCode(), message, Boolean.FALSE, null);
    }

    public static <T> WebResponse<T> error(ErrorCode errorCode, T data) {
        return new WebResponse<>(errorCode.getCode(), errorCode.getMessage(), Boolean.FALSE, data);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
