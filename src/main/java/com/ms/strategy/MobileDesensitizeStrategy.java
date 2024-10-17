package com.ms.strategy;

import cn.hutool.core.util.DesensitizedUtil;
import com.ms.enums.DesensitizationTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @author maoshan-classmate
 * @date 2024/10/17 14:28
 * @version 1.0
 */
@Component
public class MobileDesensitizeStrategy extends AbstractDesensitizeStrategy{


    @Override
    public String desensitizeDate(String content) {
        return "";
    }

    @Override
    public boolean getDetailStrategy(Integer type) {
        return DesensitizationTypeEnum.MOBILE.getType().equals(type);
    }
}
