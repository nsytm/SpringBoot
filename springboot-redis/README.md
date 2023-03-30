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
```

##### 2.2 基于Redisson实现

```java

```