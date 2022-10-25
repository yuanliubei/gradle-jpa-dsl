package com.example.demo.frame;

/**
 * @author yuanlb
 * @since 2022/10/25
 */
public enum CommonResultCode implements ResultSource{

    SUCCESS(0,"操作成功"),

    ERROR_DEFAULT(10000, "未知错误"),

    ;


    private int code;

    private String message;

    CommonResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ResultException exception(){
        return new ResultException(this);
    }


    public ResultException exception(String message){
        return new ResultException(this,message);
    }

}
