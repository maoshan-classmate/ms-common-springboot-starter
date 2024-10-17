package com.ms.context;

import com.ms.enums.DesensitizationTypeEnum;
import com.ms.strategy.AbstractDesensitizeStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author maoshan-classmate
 * @date 2024/10/17 14:29
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class DesensitizeContext {

    /**
     *     脱敏策略
     */
    @Resource
    private List<AbstractDesensitizeStrategy> desensitizeStrategyList;


    /**
     * 根据脱敏类型获取对应的脱敏策略
     * @param type 脱敏类型
     * @return 具体的脱敏策略
     */
    public AbstractDesensitizeStrategy getDesensitizeStrategy(DesensitizationTypeEnum type) {
        return desensitizeStrategyList.stream().filter(item -> item.getDetailStrategy(type)).findFirst().orElse(null);
    }
}
