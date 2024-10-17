package com.ms.context;

import com.ms.enums.SensitizationTypeEnum;
import com.ms.strategy.MobileSensitizeStrategy;
import com.ms.strategy.SensitizeStrategy;
import java.util.HashMap;
import java.util.Map;


public class SensitizeContext {

    private static  SensitizeContext instance;

    /**
     * 脱敏策略
     */
    private final Map<SensitizationTypeEnum, SensitizeStrategy> mappingStrategy = new HashMap<>();

    private SensitizeContext() {
        mappingStrategy.put(SensitizationTypeEnum.MOBILE, new MobileSensitizeStrategy());
    }

    public SensitizeStrategy getStrategy(SensitizationTypeEnum type) {
        return mappingStrategy.get(type);
    }

    public static SensitizeContext getInstance() {
        if (instance == null){
            return new SensitizeContext();
        }
        return instance;
    }
}
