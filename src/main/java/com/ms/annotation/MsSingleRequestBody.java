package com.ms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc 同@RequestBody ,用于突破@RequestBody的单体限制，并优化单传参数
 * @author maoshan-classmate
 * @date 2024/9/2 21:31
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsSingleRequestBody {

    /**
     * 参数是否必传
     */
    boolean required() default true;
}
