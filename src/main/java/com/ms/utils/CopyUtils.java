package com.ms.utils;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;
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
}
