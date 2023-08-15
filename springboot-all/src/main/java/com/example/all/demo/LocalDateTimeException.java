package com.example.all.demo;

import com.example.all.entity.txt.User;
import com.example.all.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

/**
 * @author pgl
 * @description 描述
 * @date 2023/8/15
 */
public class LocalDateTimeException {


    public static void main(String[] args) {
        User user = new User();
        user.setId(111111L);
        user.setName("张三");
        user.setCreateTime(LocalDateTime.now());

        // LocalDateTime序列化报错
        // com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Java 8 date/time type `java.time.LocalDateTime` not supported by default: add Module "com.fasterxml.jackson.datatype:jackson-datatype-jsr310" to enable handling (through reference chain: com.example.all.entity.txt.User["createTime"])
        String jsonStr = JsonUtils.toJson(user);
        String jsonStrError = toJson(user);
        System.out.println(jsonStr);
        System.out.println(jsonStrError);

        // 解决方案一: 属性添加注解 @JsonSerialize(using = LocalDateTimeSerializer.class)、@JsonDeserialize(using = LocalDateTimeDeserializer.class)
        // 解决方案二: ObjectMapper 注册 JavaTimeModule

    }

    public static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
