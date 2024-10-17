package com.ms.serializer;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ms.annotation.MsDesensitization;
import com.ms.context.DesensitizeContext;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;

/**
 * @description 自定义脱敏序列化器
 * @author maoshan-classmate
 * @date 2024/10/15 16:23
 * @version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
public class DesensitizationSerialize extends JsonSerializer<String> implements ContextualSerializer {

    @Resource
    private  DesensitizeContext desensitizeContext;



    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

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
                    //TODO 返回自定义的序列化器
                    return new DesensitizationSerialize();
                }
            }
            return prov.findValueSerializer(property.getType(), property);
        }
        return prov.findNullValueSerializer(null);
    }
}
