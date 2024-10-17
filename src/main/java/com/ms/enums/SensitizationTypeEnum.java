package com.ms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @description 脱敏类型枚举
 * @date 2024/10/15 15:44
 */
@Getter
@AllArgsConstructor
public enum SensitizationTypeEnum {

    /**
     * 自定义
     */
    CUSTOM,

    /**
     * 手机号
     */
    MOBILE,

    /**
     * 身份证
     */
    ID_CARD,

    /**
     * 邮箱
     */
    EMAIL,

    /**
     * 银行卡
     */
    BANK_CARD,

    /**
     * 地址
     */
    ADDRESS,

    /**
     * 姓名
     */
    NAME,

    /**
     * 密码
     */
    PASSWORD


}
