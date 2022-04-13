package com.moon.ratelimiter.exception;

/**
 * @author Chanmoey
 * @date 2022年04月12日
 */
public class LimitException extends RuntimeException{

    public LimitException(String message) {
        super(message);
    }
}
