package com.moon.ratelimiter.annotation;

import com.google.common.collect.Lists;
import com.moon.ratelimiter.exception.LimitException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Chanmoey
 * @date 2022年04月12日
 */
@Aspect
@Component
public class LimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> luaScript;

    @Pointcut("@annotation(com.moon.ratelimiter.annotation.Limiter)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Limiter annotation = method.getAnnotation(Limiter.class);

        if (annotation == null) {
            return;
        }

        String key = annotation.key();
        int limit = annotation.limit();

        if (StringUtils.isEmpty(key)) {
            Class[] type = method.getParameterTypes();
            key = method.getName();

            if (type.length > 0) {
                String paramTypes = Arrays.stream(type)
                        .map(Class::getName)
                        .collect(Collectors.joining("&"));

                key += "#" + paramTypes;
            }
        }

        boolean acquired = Boolean.TRUE.equals(this.stringRedisTemplate.execute(
                this.luaScript,
                Lists.newArrayList(key),
                String.valueOf(limit)
        ));

        if (!acquired) {
            throw new LimitException("Maximum access limit reached!");
        }
    }
}
