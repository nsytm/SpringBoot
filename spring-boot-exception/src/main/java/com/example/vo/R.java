package com.example.vo;

import com.example.exception.IExceptionEnum;
import com.example.exception.WebResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 天纵神威
 * @date 2024/11/14
 * @description 统一返回类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {

    private int code;

    private String msg;

    private T data;

    private R(IExceptionEnum exceptionEnum, T data) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R<>(WebResultEnum.OK, null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(WebResultEnum.OK, data);
    }

    public static <T> R<T> ok(String msg) {
        return new R<>(WebResultEnum.OK.getCode(), msg, null);
    }

    public static <T> R<T> ok(String msg, T data) {
        return new R<>(WebResultEnum.OK.getCode(), msg, data);
    }

    public static <T> R<T> fail() {
        return new R<>(WebResultEnum.INTERNAL_SERVER_ERROR, null);
    }

    public static <T> R<T> fail(String msg) {
        return new R<>(WebResultEnum.INTERNAL_SERVER_ERROR.getCode(), msg, null);
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, msg, null);
    }

    public static <T> R<T> fail(IExceptionEnum exceptionEnum) {
        return new R<>(exceptionEnum, null);
    }

}