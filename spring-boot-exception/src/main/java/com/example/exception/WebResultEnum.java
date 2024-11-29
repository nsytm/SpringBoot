package com.example.exception;

/**
 * @author 天纵神威
 * @date 2024/11/29
 * @description Web 层异常枚举
 */
public enum WebResultEnum implements IExceptionEnum {

    OK(200, "接口调用成功"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private final int code;

    private final String msg;

    WebResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

}
