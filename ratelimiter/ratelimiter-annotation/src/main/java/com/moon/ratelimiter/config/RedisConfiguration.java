package com.moon.ratelimiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author Chanmoey
 * @date 2022年04月12日
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Bean
    public DefaultRedisScript<Boolean> loadRedisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("script/lua/limiter.lua"));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
