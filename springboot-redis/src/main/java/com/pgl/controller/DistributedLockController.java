package com.pgl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author pgl
 * @ClassName DistributedLockController
 * @Description 基于Redis实现分布式锁
 * @date:2023/3/29
 */
@Slf4j
@RestController
public class DistributedLockController {

    private final RedisTemplate<String, Object> redisTemplate;

    public DistributedLockController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 单机部署
     * 问题: 如何处理并发带来的线程安全问题
     * 解决方案: 使用 synchronized 关键字
     */
    @GetMapping("/redis/deductStockSyn")
    public String deductStockSyn() {

        synchronized (this) {
            Object value = redisTemplate.opsForValue().get("stock");
            if (null == value) {
                log.error("value is not null");
                return "error";
            }
            // 剩余库存
            int stock = Integer.parseInt(value.toString());
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减成功, 剩余库存: {}", realStock);
                return "success";
            } else {
                log.info("扣减失败, 库存不足");
                return "error";
            }
        }

    }

    /**
     * 集群部署
     * 问题: 如何处理并发带来的线程安全问题
     * 解决方案: 使用redis分布式锁
     */
    @GetMapping("/redis/deductStockRed")
    public String deductStockRedis() {

        String lockKey = "redis:product:id";
        String uuid = UUID.randomUUID().toString();
        Boolean bool = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 10, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(bool)) {
            return "error";
        }
        try {
            Object value = redisTemplate.opsForValue().get("stock");
            if (null == value) {
                log.error("value is not null");
                return "error";
            }
            // 剩余库存
            int stock = Integer.parseInt(value.toString());
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                log.info("扣减成功, 剩余库存: {}", realStock);
                return "success";
            } else {
                log.info("扣减失败, 库存不足");
                return "error";
            }
        } finally {
            if (uuid.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }

    }

}
