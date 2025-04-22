package com.ms.utils;

import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class JSONUtils {

    private JSONUtils() {
       // 工具类不允许被实例化
        throw new AssertionError();
    }



    /**
     * 根据传入的类型返回空集合或空对象
     *
     * @param typeRef 传入类型
     * @param <T>     传入类型
     * @return 返回空集合或空对象
     */
    private static <T> T getDefaultInstance(TypeReference<T> typeRef) {
        Type type = typeRef.getType();
        Class<?> rawType;
        if (type instanceof Class) {
            rawType = (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            rawType = (Class<?>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException("不支持的类型: " + type);
        }
        if (Collection.class.isAssignableFrom(rawType)) {
            return (T) Collections.emptyList();
        } else if (Map.class.isAssignableFrom(rawType)) {
            return (T) Collections.emptyMap();
        } else {
            try {
                return (T) rawType.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("无法创建默认实例: " + rawType.getName(), e);
            }
        }
    }


    /**
     * 根据JSON字符串反序列化成对应的Java对象
     *
     * @param jsonString JSON字符串
     * @param typeRef 返回类型
     * @param <T>     返回类型
     * @return 对应的Java对象
     */
    public static <T> T getBeanByJSONString(String jsonString, TypeReference<T> typeRef){
        T result;
        if (CharSequenceUtil.isBlank(jsonString)){
            result = getDefaultInstance(typeRef);
        } else {
            try {
                result = JSON.parseObject(jsonString, typeRef);
            } catch (Exception e) {
                throw new RuntimeException("JSON字符串解析失败:{}", e);
            }
        }
        return result;
    }
}
