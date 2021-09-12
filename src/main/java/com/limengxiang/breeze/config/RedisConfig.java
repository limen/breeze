package com.limengxiang.breeze.config;

import com.limengxiang.breeze.utils.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
@Configuration
@Slf4j
public class RedisConfig {

    public RedisConfig() {
        log.info("inited");
    }

    private static class RedisTemplateCondition implements Condition {

        @Override
        public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
            return conditionContext.getEnvironment().getProperty("breeze.deploy-mode").equals("multi");
        }
    }

    private static class TypeAwareValueSerializer implements RedisSerializer<Object> {

        @Override
        public byte[] serialize(Object o) throws SerializationException {
            if (o == null) {
                throw new SerializationException("Null not allowed");
            }
            if (o instanceof String) {
                return ((String) o).getBytes(StandardCharsets.UTF_8);
            } else if (o instanceof Long) {
                return ((Long) o).toString().getBytes(StandardCharsets.UTF_8);
            }
            return StrUtil.valueOf(o).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
        }
    }

    @Bean
    @Conditional(RedisTemplateCondition.class)
    @Qualifier("redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置Key使用String序列化
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(new TypeAwareValueSerializer());
        return redisTemplate;
    }

}
