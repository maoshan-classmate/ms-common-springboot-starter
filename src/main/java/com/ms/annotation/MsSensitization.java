package com.ms.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ms.enums.SensitizationTypeEnum;
import com.ms.serializer.SensitizationSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @description 数据脱敏注解
 * @date 2024/10/15 16:27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitizationSerialize.class)
public @interface MsSensitization {

    /**
     * 脱敏数据类型，CUSTOM注解下，startInclude和endExclude生效
     */
    SensitizationTypeEnum type() default SensitizationTypeEnum.CUSTOM;

    /**
     * 是否使用正则表达式进行脱敏
     */
    boolean enableRegex() default false;

    /**
     * 脱敏开始位置（包含）
     */
    int startInclude() default 0;

    /**
     * 脱敏结束位置（不包含）
     */
    int endExclude() default 0;

    /**
     * 脱敏使用的特殊字符
     */
    String customChar() default "*";

    /**
     * 正则表达式进行脱敏
     */
    String customRegex() default "";

    /**
     * 正则表达式进行替换
     */
    String customReplace() default "";

}
