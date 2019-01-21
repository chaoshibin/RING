package com.ring.core.config;

import org.redisson.Redisson;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;

/**
 * @author chaoshibin
 */
@Configuration
public class RedisConfig {
    @Autowired
    private RedisConnectionFactory factory;
    @Autowired
    private RedisProperties redisProperties;


    @Bean
    public RedissonClient redisClient(){
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                //空字符串会报错
                .setPassword(null);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }

/*    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://10.50.10.203:6379")
                .setPassword(null);
        RedissonClient redissonClient = Redisson.create(config);
        RLock lock = redissonClient.getLock("ke");
        lock.tryLock();
        lock.unlock();
    }*/

    @Bean
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public ValueOperations<String, String> valueOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public ListOperations<String, String> listOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    public SetOperations<String, String> setOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, String> zSetOperations(StringRedisTemplate redisTemplate) {
        return redisTemplate.opsForZSet();
    }
}
