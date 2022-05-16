package com.moon.ratelimiter.controller;

import com.moon.ratelimiter.annotation.Limiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Chanmoey
 * @date 2022年04月13日
 */
@RestController
@RequestMapping("/limiter")
public class Controller {

    @Limiter(limit = 2, key = "test")
    @GetMapping("/test")
    public String limit() {
        return "success";
    }
}
