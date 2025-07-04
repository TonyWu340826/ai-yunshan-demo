package com.example.aiyunshandemo.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributedLock {
    
    private final StringRedisTemplate redisTemplate;
    
    public RedisDistributedLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    /**
     * 尝试获取分布式锁
     * @param lockKey 锁的key
     * @param requestId 请求标识(可使用UUID)
     * @param expireTime 过期时间(秒)
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, long expireTime) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, requestId, expireTime, TimeUnit.SECONDS);
    }
    
    /**
     * 释放分布式锁
     * @param lockKey 锁的key
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String requestId) {
        String currentValue = redisTemplate.opsForValue().get(lockKey);
        if (currentValue != null && currentValue.equals(requestId)) {
            return redisTemplate.delete(lockKey);
        }
        return false;
    }
    
    /**
     * 带重试的获取锁
     * @param lockKey 锁的key
     * @param requestId 请求标识
     * @param expireTime 过期时间(秒)
     * @param retryTimes 重试次数
     * @param sleepMillis 每次重试间隔(毫秒)
     * @return 是否获取成功
     * @throws InterruptedException
     */
    public boolean lockWithRetry(String lockKey, String requestId, long expireTime, 
                               int retryTimes, long sleepMillis) throws InterruptedException {
        boolean result = tryLock(lockKey, requestId, expireTime);
        while (!result && retryTimes-- > 0) {
            Thread.sleep(sleepMillis);
            result = tryLock(lockKey, requestId, expireTime);
        }
        return result;
    }
}