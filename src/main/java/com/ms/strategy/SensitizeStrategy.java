package com.ms.strategy;

import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @date 2024/10/15 15:51
 */
public interface SensitizeStrategy {

    /**
     * 数据脱敏
     *
     * @param content       脱敏前数据
     * @param startInclude  脱敏开始位置（包含）
     * @param endExclude    脱敏结束位置（不包含）
     * @param customChar    脱敏使用的特殊字符
     * @param jsonGenerator JSON数据写入
     * @return 脱敏后数据
     */
    String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator) throws IOException;

}