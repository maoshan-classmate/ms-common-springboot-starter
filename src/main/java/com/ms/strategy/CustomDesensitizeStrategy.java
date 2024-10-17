package com.ms.strategy;


import com.fasterxml.jackson.core.JsonGenerator;
import com.ms.enums.DesensitizationTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @description 自定义数据脱敏策略
 * @author maoshan-classmate
 * @date 2024/10/17 17:18
 * @version 1.0
 */

@Component
public class CustomDesensitizeStrategy extends AbstractDesensitizeStrategy{



    @Override
    public void desensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator) throws IOException {

    }

    @Override
    public boolean getDetailStrategy(DesensitizationTypeEnum type) {
        return DesensitizationTypeEnum.CUSTOM.equals(type);
    }
}
