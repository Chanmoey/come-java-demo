package com.moon.tinylombok.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Chanmoey
 * @date 2022年05月31日
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface MyGetter {
}
