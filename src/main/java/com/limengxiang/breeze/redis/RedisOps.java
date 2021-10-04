package com.limengxiang.breeze.redis;

import com.limengxiang.breeze.utils.NumUtil;
import com.limengxiang.breeze.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisOps {

    private RedisTemplate redisTemplate;

    @Autowired(required = false)
    public RedisOps(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean setIfAbsent(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, StrUtil.valueOf(value));
    }

    public boolean setIfAbsent(String key, Object value, long time, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, StrUtil.valueOf(value), time, unit);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, StrUtil.valueOf(value));
    }

    public void setWithTTL(String key, Object value, long ttl) {
        redisTemplate.opsForValue().set(key, StrUtil.valueOf(value), ttl, TimeUnit.SECONDS);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Integer getInt(String key) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            return null;
        }
        return NumUtil.toInteger(o);
    }

    public Long getLong(String key) {
        Object s = redisTemplate.opsForValue().get(key);
        if (s == null) {
            return null;
        }
        return NumUtil.toLong(s);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public String getString(String key) {
        return StrUtil.valueOf(redisTemplate.opsForValue().get(key));
    }

    public boolean addMember(String key, Object mem) {
        return redisTemplate.opsForSet().add(key, StrUtil.valueOf(mem)).longValue() == 1L;
    }

    public void put(String key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, StrUtil.valueOf(field), StrUtil.valueOf(value));
    }

    public boolean putIfAbsent(String key, Object field, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, StrUtil.valueOf(field), StrUtil.valueOf(value));
    }

    public boolean addMember(String key, Object mem, double score) {
        return redisTemplate.opsForZSet().add(key, StrUtil.valueOf(mem), score);
    }

    public Long push(String key, List elems) {
        ListOperations listOperations = redisTemplate.opsForList();
        return listOperations.leftPushAll(key, elems);
    }

    public Object pop(String key) {
        ListOperations listOperations = redisTemplate.opsForList();
        return listOperations.rightPop(key);
    }

    public long listSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    public <T> T execute(String lua, Class<T> clazz, List keys, Object... args) {
        RedisScript<T> script = new DefaultRedisScript<>(lua, clazz);
        return (T) redisTemplate.execute(script, keys, args);
    }

}
