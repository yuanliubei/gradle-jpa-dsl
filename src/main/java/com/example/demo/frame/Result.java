package com.example.demo.frame;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yuanlb
 * @since 2022/10/25
 */
public class Result<T>{

    private int code;

    private String message;

    private T data;

    private String exception;

    private String exception_msg;


    public static Result error(String message) {
        return from(CommonResultCode.ERROR_DEFAULT.getCode(), message);
    }

    public static Result error() {
        return from(CommonResultCode.ERROR_DEFAULT);
    }

    public static Result ok(Object data) {
        return from(CommonResultCode.SUCCESS).data(data);
    }

    public static Result ok() {
        return from(CommonResultCode.SUCCESS);
    }

    public static Result from(ResultException e) {
        return from(e.getResultSource());
    }

    public static Result from(ResultSource resultSource) {
        return from(resultSource.getCode(), resultSource.getMessage());
    }

    public static Result from(int code, String message) {
        Result<Object> r = new Result<>();
        r.code = code;
        r.message = message;
        return r;
    }

    public Result code(int code) {
        this.code = code;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result data(T data) {
        this.data = data;
        return this;
    }

    public Result exception(Exception e) {
        if (StringUtils.isNotEmpty(e.getLocalizedMessage())) {
            this.exception_msg = e.getLocalizedMessage();
        }
        this.exception = e.getClass().getName();
        return this;
    }

    public boolean isOK() {
        return code == CommonResultCode.SUCCESS.getCode();
    }

}
