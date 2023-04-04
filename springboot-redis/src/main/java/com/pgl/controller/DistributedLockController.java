package com.pgl.controller;

import com.pgl.common.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    private final RedissonClient redissonClient;

    public DistributedLockController(RedisTemplate<String, Object> redisTemplate, RedissonClient redissonClient) {
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
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
    @GetMapping("/redis/deductStockRed/{id}")
    public String deductStockRedis(@PathVariable("id") Long id) {

        String lockKey = RedisConstants.REDIS_PRODUCT + id;
        String uuid = UUID.randomUUID().toString();

        // 加锁
        Boolean bool = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 10, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(bool)) {
            log.error("code is running, please try again later");
            return "error";
        }
        try {

            // 业务逻辑
            return deductStock();

        } catch (Exception e) {
            log.error("code run error", e);
            return "error";
        } finally {
            // 防止误删锁
            if (uuid.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }

    }

    /**
     * 终极版 5.0.0
     * 基于redisson实现分布式锁
     */
    @GetMapping("/redis/deductStockSon/{id}")
    public String deductStockRedisson(@PathVariable("id") Long id) {

        String lockKey = RedisConstants.REDIS_PRODUCT + id;

        // 获取锁对象
        RLock lock = redissonClient.getLock(lockKey);
        try {
            // 尝试在 5 秒内加锁, 直到锁可用为止
            boolean flag = lock.tryLock(5, 10, TimeUnit.SECONDS);
            if (!flag) {
                log.error("code is running, please try again later");
                return "error";
            }

            // 业务逻辑
            return deductStock();

        } catch (Exception e) {
            log.error("code run error", e);
            return "error";
        } finally {
            // 释放锁
            lock.unlock();
        }
    }

    private String deductStock() {
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


    // TODO: 2023/3/31 锁的key和请求参数


}
