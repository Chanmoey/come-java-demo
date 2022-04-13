package com.moon.ratelimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Chanmoey
 * @date 2022年04月12日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Limiter {
    /**
     * 限流速率，每秒可响应多少访问。
     * @return int。
     */
    int limit();

    /**
     * 限流关键字。
     * @return String。
     */
    String key() default "";
}
