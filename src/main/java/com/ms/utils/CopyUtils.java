package com.ms.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @date 2024/9/19 10:55
 */
public class CopyUtils extends BeanUtil {

    /**
     * 复制集合
     *
     * @param sources 原集合
     * @param target  目标对象
     * @param <S>     原对象类型
     * @param <T>     目标对象类型
     * @return 复制后的集合
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }

    /**
     * 复制集合并进行拓展
     *
     * @param sources  原集合
     * @param target   目标对象
     * @param callBack 回调
     * @param <S>      原对象类型
     * @param <T>      目标对象类型
     * @return 复制后的集合
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, BiConsumer<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            if (callBack != null) {
                // 回调
                callBack.accept(source, t);
            }
            list.add(t);
        }
        return list;
    }

    /**
     * 根据条件复制集合并进行拓展
     *
     * @param sources   原集合
     * @param target    目标对象
     * @param condition 复制条件
     * @param callBack  回调
     * @param <S>       原对象类型
     * @param <T>       目标对象类型
     * @return 复制后的集合
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, Predicate<S> condition, BiConsumer<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            //添加判断条件
            if (condition.test(source) && (callBack != null)) {
                T t = target.get();
                copyProperties(source, t);
                // 回调
                callBack.accept(source, t);
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 将json转换为Java对象(支持复杂类型)
     *
     * @param json    json字符串
     * @param typeRef 要转化的Java对象的类型
     * @param <T>     要转化的Java对象的类型
     * @return Java对象
     */
    public static <T> T jsonToBean(String json, TypeReference<T> typeRef) {
        if (CharSequenceUtil.isBlank(json)) {
            return getDefaultInstance(typeRef);
        }
        try {
            return JSON.parseObject(json, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("json转换异常", e);
        }
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
}
