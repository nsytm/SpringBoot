package com.example.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author 天纵神威
 * @date 2024/11/6
 * @description 功能描述
 */
@Data
@Accessors(chain = true)
public class LogVO {

    /**
     * Log表主键ID
     */
    private Long logId;

    /**
     * 请求内容
     */
    private String title;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人姓名
     */
    private String username;

    /**
     * 请求IP
     */
    private String ip;

    /**
     * 请求URL
     */
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应参数
     */
    private String responseParam;

    /**
     * 请求响应状态
     */
    private int status;

    /**
     * 响应失败信息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime;

    /**
     * 执行时长，毫秒
     */
    private long executionTime;

}