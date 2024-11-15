package com.example.vo;

import lombok.Data;

/**
 * @author 天纵神威
 * @date 2024/11/14
 * @description 统一返回类
 */
@Data
public class R<T> {

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok(T data) {
        R<T> r = new R<T>();
        r.setCode(200);
        r.setData(data);
        return r;
    }

    public static <T> R<T> ok(T data, String msg) {
        R<T> r = new R<T>();
        r.setCode(200);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(String msg) {
        R<T> r = new R<T>();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(int code, String msg) {
        R<T> r = new R<T>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

}