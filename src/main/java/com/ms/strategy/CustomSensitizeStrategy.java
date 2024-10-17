package com.ms.strategy;


import com.fasterxml.jackson.core.JsonGenerator;

import java.io.IOException;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @description 自定义数据脱敏策略
 * @date 2024/10/17 17:18
 */

public class CustomSensitizeStrategy implements SensitizeStrategy {


    @Override
    public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator) throws IOException {
        return "";
    }


}
