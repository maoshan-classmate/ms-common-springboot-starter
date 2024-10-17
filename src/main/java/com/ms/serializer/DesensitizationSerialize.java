package com.ms.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ms.annotation.MsDesensitization;
import com.ms.context.DesensitizeContext;
import com.ms.enums.DesensitizationTypeEnum;
import com.ms.strategy.AbstractDesensitizeStrategy;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @description 自定义脱敏序列化器
 * @date 2024/10/15 16:23
 */

@NoArgsConstructor
public class DesensitizationSerialize extends JsonSerializer<String> implements ContextualSerializer {

    @Resource
    private DesensitizeContext desensitizeContext;

    private DesensitizationTypeEnum desensitizationType;

    private int startInclude;

    private int endExclude;

    private String customChar;

    public DesensitizationSerialize(DesensitizationTypeEnum desensitizationType, int startInclude, int endExclude, String customChar) {
        this.desensitizationType = desensitizationType;
        this.startInclude = startInclude;
        this.endExclude = endExclude;
        this.customChar = customChar;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        AbstractDesensitizeStrategy desensitizeStrategy = desensitizeContext.getDesensitizeStrategy(desensitizationType);
        if (desensitizeStrategy == null) {
            throw new IllegalArgumentException("未定义的脱敏数据类型： " + desensitizationType);
        }
        desensitizeStrategy.desensitizeDate(value, startInclude, endExclude, customChar, gen);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            // 判断数据类型是否为String类型
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                // 获取定义的注解
                MsDesensitization msDesensitization = property.getAnnotation(MsDesensitization.class);
                if (msDesensitization == null) {
                    msDesensitization = property.getContextAnnotation(MsDesensitization.class);
                }
                if (msDesensitization != null) {
                    //返回自定义的序列化器
                    return new DesensitizationSerialize(msDesensitization.type(), msDesensitization.startInclude(),
                            msDesensitization.endExclude(), msDesensitization.customChar());
                }
            }
            return prov.findValueSerializer(property.getType(), property);
        }
        return prov.findNullValueSerializer(null);
    }
}
