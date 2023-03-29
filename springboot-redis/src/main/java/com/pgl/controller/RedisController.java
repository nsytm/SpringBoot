package com.pgl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * @author pgl
 * @ClassName RedisController
 * @Description 使用RedisTemplate工具类操作redis
 * @date:2023/3/27
 */
@RestController
public class RedisController {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 基于构造方法注入bean
     */
    public RedisController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //@Autowired
    //private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis/stringTest")
    public String stringTest() {
        if (Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent("test01", "1111", Duration.ofSeconds(10)))) {

            // 业务代码

            return "success";
        }
        return "error";
    }


}
