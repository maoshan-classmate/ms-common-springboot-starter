package com.ms.utils;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ms.dto.CompareDTO;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

public  class CompareUtils {

    private CompareUtils() {
        throw new AssertionError("工具类不允许被实例化");
    }


    /**
     * 比较对象变更
     * @param oldBean 旧对象
     * @param newBean 新对象
     * @return 对象变更信息
     * @param <T> 对象类型
     */
    public static <T> CompareDTO compareBean(T oldBean, T newBean, String... ignoreFields) {
        CompareDTO compare = new CompareDTO();
        HashMap<String, Object> oldValueMap = new HashMap<>();
        HashMap<String, Object> newValueMap = new HashMap<>();
        HashMap<String, Object> noChangeValueMap = new HashMap<>();
        List<String> ignoreValueList = new ArrayList<>();
        if (ignoreFields != null && ignoreFields.length > 0){
            ignoreValueList= Arrays.asList(ignoreFields);
        }
        if (oldBean == null && newBean == null) {
            compare.setChange(false);
        } else if (oldBean == null) {
            newValueMap.putAll(BeanUtil.beanToMap(newBean));
        } else if (newBean == null) {
            oldValueMap.putAll(BeanUtil.beanToMap(oldBean));
        } else {
            setCompareValue(oldValueMap, newValueMap,noChangeValueMap,ignoreValueList,
                    oldBean, newBean, compare, CollUtil.isNotEmpty(ignoreValueList) ? List::contains : null);
        }
        return compare;
    }


    /**
     * 设置比较的值
     * @param oldValueMap 改变前的值Map
     * @param newValueMap 改变后的值Map
     * @param noChangeValueMap 未改变的值Map
     * @param ignoreValueList 忽略比较的属性集合
     * @param oldBean 变更前的对象
     * @param newBean 变更后的对象
     * @param compare 对象变更信息
     * @param predicate 额外比较条件
     * @param <T> 对象类型
     */
    private static <T> void setCompareValue(Map<String, Object> oldValueMap,
                                            Map<String, Object> newValueMap,
                                            Map<String, Object> noChangeValueMap,
                                            List<String> ignoreValueList,
                                            T oldBean, T newBean, CompareDTO compare,
                                            BiPredicate<List<String>,String> predicate) {
        List<String> ignoreFieldList = new ArrayList<>();
        Field[] oldFields = oldBean.getClass().getDeclaredFields();
        for (Field field : oldFields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (predicate != null && predicate.test(ignoreValueList,field.getName())){
                ignoreFieldList.add(field.getName());
                continue;
            }
            field.setAccessible(true);
            try {
                Object oldValue = field.get(oldBean);
                Object newValue = field.get(newBean);
                if (oldValue == null && newValue == null) {
                    noChangeValueMap.put(field.getName(), null);
                } else if (!ObjectUtil.equals(oldValue, newValue)) {
                    oldValueMap.put(field.getName(), oldValue);
                    newValueMap.put(field.getName(), newValue);
                }else{
                    noChangeValueMap.put(field.getName(), oldValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        compare.setOldValueMap(oldValueMap);
        compare.setNewValueMap(newValueMap);
        compare.setNoChangeValueMap(noChangeValueMap);
        compare.setIgnoreFieldList(ignoreFieldList);
        if (MapUtil.isEmpty(oldValueMap) && MapUtil.isEmpty(newValueMap)){
            compare.setChange(false);
        }
    }


}
