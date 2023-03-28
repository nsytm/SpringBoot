package com.pgl.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author pgl
 * @ClassName RedisConfig
 * @Description RedisConfig 配置类
 * @date:2023/3/24
 */
@Configuration
public class RedisConfig {

    /**
     * RedisTemplate 相关配置
     *
     * @return RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(@Autowired RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // 设置连接工厂
        template.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = jackson2JsonRedisSerializerConfig();
        // jackson序列化 默认配置
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

        // 使用StringRedisSerializer来序列化和反序列化redis的key值
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);

        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);

        // value采用jackson序列化方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //template.setValueSerializer(genericJackson2JsonRedisSerializer);

        // hash的value也采用jackson序列化方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //template.setHashValueSerializer(genericJackson2JsonRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }

    /**
     * jackson序列化 自定义配置
     *
     * @return Jackson2JsonRedisSerializer<Object>
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializerConfig() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

}
