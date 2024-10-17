package com.ms.strategy;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.ms.enums.DesensitizationTypeEnum;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author maoshan-classmate
 * @date 2024/10/17 14:28
 * @version 1.0
 */
@Component
public class MobileDesensitizeStrategy extends AbstractDesensitizeStrategy{


    @Override
    public void desensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator) throws IOException {
        jsonGenerator.writeString(DesensitizedUtil.mobilePhone(content));
    }

    @Override
    public boolean getDetailStrategy(DesensitizationTypeEnum type) {
        return DesensitizationTypeEnum.MOBILE.equals(type);
    }
}
