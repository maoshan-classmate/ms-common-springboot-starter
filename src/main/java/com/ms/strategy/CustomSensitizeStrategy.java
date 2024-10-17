package com.ms.strategy;


import com.fasterxml.jackson.core.JsonGenerator;
import com.ms.enums.SensitizationTypeEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @description 自定义数据脱敏策略
 * @author maoshan-classmate
 * @date 2024/10/17 17:18
 * @version 1.0
 */

@Component
public class CustomSensitizeStrategy implements SensitizeStrategy{


    @Override
    public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator) throws IOException {
        return "";
    }

    @Override
    public boolean getDetailStrategy(SensitizationTypeEnum type) {
        return false;
    }
}
