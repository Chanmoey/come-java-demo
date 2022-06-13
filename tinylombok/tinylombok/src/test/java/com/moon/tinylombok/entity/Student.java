package com.moon.tinylombok.entity;

import com.moon.tinylombok.annotation.MyGetter;

/**
 * @author Chanmoey
 * @date 2022年06月01日
 */
@MyGetter
public class Student {

    private final String name;
    private final Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
