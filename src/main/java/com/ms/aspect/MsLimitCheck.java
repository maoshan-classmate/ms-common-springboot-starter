package com.ms.aspect;

import java.lang.annotation.*;

/**
 * @author maoshan-classmate
 * @date 2024/12/29 23:24
 * @desc 限流注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MsLimitCheck {

    /**
     * 控制粒度(默认：IP address)
     */
    String lockKey() default "";

    /**
     * 重试次数
     */
    int count();

    /**
     * 重试时间
     */
    int expireCache();


    /**
     * 错误文案
     */
    String errorMessage() default "请求太频繁，请稍后再试";

}
