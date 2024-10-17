package com.ms.serializer;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.ms.annotation.MsSensitization;
import com.ms.context.SensitizeContext;
import com.ms.enums.SensitizationTypeEnum;
import com.ms.strategy.SensitizeStrategy;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @description 自定义脱敏序列化器
 * @date 2024/10/15 16:23
 */
@Slf4j
@Setter
@NoArgsConstructor(force = true)
public class SensitizationSerialize extends JsonSerializer<String> implements ContextualSerializer {


    private  SensitizationTypeEnum sensitizationType;

    private final int startInclude;

    private final int endExclude;

    private final String customChar;




    public SensitizationSerialize(Integer startInclude, Integer endExclude, String customChar) {
        this.startInclude = startInclude;
        this.endExclude = endExclude;
        this.customChar = customChar;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        SensitizeStrategy sensitizeStrategy = SensitizeContext.getInstance().getStrategy(sensitizationType);
        if (sensitizeStrategy != null) {
            String sensitizeDate = sensitizeStrategy.sensitizeDate(value, startInclude, endExclude, customChar, gen);
            log.info("脱敏前数据为：{}", value);
            log.info("脱敏后数据为：{}", sensitizeDate);
        }

    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            // 判断数据类型是否为String类型
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                // 获取定义的注解
                MsSensitization msSensitization = property.getAnnotation(MsSensitization.class);
                if (msSensitization == null) {
                    msSensitization = property.getContextAnnotation(MsSensitization.class);
                }
                if (msSensitization != null) {
                    sensitizationType=msSensitization.type();
                    SensitizationSerialize customSensitization = createCustomSensitization(msSensitization.type(), msSensitization.startInclude(),
                            msSensitization.endExclude(), msSensitization.customChar(), msSensitization.enableRegex(), msSensitization.customRegex());
                    return customSensitization != null ? customSensitization :  prov.findValueSerializer(property.getType(), property);
                }
            }
            return prov.findValueSerializer(property.getType(), property);
        }
        return prov.findNullValueSerializer(null);
    }


    /**
     * 构建自定义序列化器
     * @param sensitizationType 脱敏数据类型
     * @param startInclude 脱敏开始位置（包含）
     * @param endExclude 脱敏结束位置（不包含）
     * @param customChar 脱敏使用的特殊字符
     * @param enableRegex 是否使用正则表达式进行脱敏
     * @param customRegex 正则表达式进行脱敏
     * @return 自定义序列化器
     */
    private SensitizationSerialize createCustomSensitization(SensitizationTypeEnum sensitizationType, int startInclude, int endExclude,
                                                                 String customChar, boolean enableRegex, String customRegex) {
        SensitizationSerialize sensitizationSerialize = new SensitizationSerialize(startInclude, endExclude, customChar);
        if (SensitizationTypeEnum.CUSTOM == sensitizationType && enableRegex){
            //优先使用自定义正则表达式
            if (StrUtil.isBlank(customRegex)){
                return null;
            }
        }
        sensitizationSerialize.sensitizationType = sensitizationType;
        return sensitizationSerialize;
    }
}
