package com.ms.strategy;

import com.fasterxml.jackson.core.JsonGenerator;
import com.ms.enums.DesensitizationTypeEnum;

import java.io.IOException;

/**
 * @author maoshan-classmate
 * @date 2024/10/15 15:51
 * @version 1.0
 */
public abstract class AbstractDesensitizeStrategy {

    /**
     * 数据脱敏
     * @param content 脱敏前数据
     * @param startInclude 脱敏开始位置（包含）
     * @param endExclude 脱敏结束位置（不包含）
     * @param customChar 脱敏使用的特殊字符
     * @param jsonGenerator JSON数据写入
     */
    public abstract void desensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator) throws IOException;

    /**
     * 判断具体的脱敏策略
     * @param type 脱敏类型
     * @return 是否匹配脱敏策略
     */
    public abstract boolean  getDetailStrategy (DesensitizationTypeEnum type);
}
