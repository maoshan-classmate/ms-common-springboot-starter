package com.ms.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CompareDTO {

    /**
     * 是否发生变更
     */
    private boolean change = true;

    /**
     * 变更前的值
     */
    private Map<String,Object> oldValueMap;

    /**
     * 变更后的值
     */
    private Map<String,Object> newValueMap;

    /**
     * 未变更的值
     */
    private Map<String,Object> noChangeValueMap;

    /**
     * 忽略比较的值
     */
    private List<String> ignoreFieldList;
}
