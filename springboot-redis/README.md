### springboot-redis

 ---

#### 1.整合redis

##### 1.1 引入依赖

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <version>2.7.9</version>
</dependency>
```

##### 1.2 配置yaml文件

```yaml
spring:
  redis:
    # redis默认情况下有16个分片，这里配置具体使用的分片，默认是0
    database: 0
    # 服务器 IP
    host: 127.0.0.1
    # 服务器连接端口
    port: 6379
    # 服务器连接密码（默认为空）
    password:
    # 连接超时时间（毫秒）
    timeout: 3000
```

##### 1.3 编写配置类

```java

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        // 使用jackson序列化的默认策略
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        template.afterPropertiesSet();

        return template;
    }
}
```

#### 2.基于Redis实现分布式锁

##### 2.1 基于RedisTemplate实现

```java

@RestController
public class DistributedLockController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

}
```

##### 2.2 基于Redisson实现

```java

@RestController
public class DistributedLockController {

    @Autowired
    private RedissonClient redissonClient;

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


}
```

#### 5.异常

##### 5.1 redis未配置密码, springboot集成redisson启动报错

```yaml
spring:
  redis:
    database: 5
    host: 127.0.0.1
    port: 6379
    password:
```

```text
异常信息:
 org.redisson.client.RedisConnectionException: Unable to connect to Redis server: localhost/127.0.0.1:6379
 org.redisson.client.RedisException: ERR Client sent AUTH, but no password is set.
 
解决方案:
 注释 password: 或者 配置RedissonConfig类, 重新实现RedissonClient
```