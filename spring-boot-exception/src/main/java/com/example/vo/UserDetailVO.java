package com.example.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 天纵神威
 * @date 2024/11/28
 * @description 功能描述
 */
@Data
public class UserDetailVO implements Serializable {

    // @Serial 注解是 Java 14 引入的一个注解，用于标记序列化相关的方法或字段。
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @NotBlank(message = "用户状态不能为空")
    private String status;

}
