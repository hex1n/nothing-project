package com.hexin.demo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author hex1n
 * @Date 2021/10/21 9:35
 * @Description
 */
@Data
public class ResultBean implements Serializable {
    private static final long serialVersionUID = 9218812351323900849L;
    private int code;
    private String message;
    private Object data;

    public ResultBean() {
    }

    public ResultBean(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public ResultBean(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public ResultBean(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }

    public static ResultBean build(int code, String message, Object data) {
        return new ResultBean(code, message, data);
    }

    public static ResultBean success(Object data) {
        return new ResultBean("success", data);
    }

    public static ResultBean success(String message, Object data) {
        return new ResultBean(message, data);
    }

    public static ResultBean success() {
        return new ResultBean("success", (Object) "");
    }

    public static ResultBean error(Object data) {
        return new ResultBean(500, "error", data);
    }

    public static ResultBean error(String message) {
        return new ResultBean(500, message, (Object) "");
    }

    public static ResultBean error() {
        return new ResultBean(500, "error", (Object) "");
    }

    public static ResultBean error(int code, String message) {
        return new ResultBean(code, message);
    }

}
