package com.ms.strategy;

/**
 * @author maoshan-classmate
 * @date 2024/10/15 15:51
 * @version 1.0
 */
public abstract class AbstractDesensitizeStrategy {

    /**
     * 数据脱敏
     * @param content 脱敏前数据
     * @return 脱敏后数据
     */
    public abstract String desensitizeDate(String content);

    /**
     * 判断具体的脱敏策略
     * @param type 脱敏类型
     * @return 是否匹配脱敏策略
     */
    public abstract boolean  getDetailStrategy (Integer type);
}
