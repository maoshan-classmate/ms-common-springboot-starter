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
public enum DesensitizationTypeEnum {

    /**
     * 自定义
     */
    CUSTOM("custom", 0),

    /**
     * 手机号
     */
    MOBILE("mobile",1),

    /**
     * 身份证
     */
    ID_CARD("idCard",2),

    /**
     * 邮箱
     */
    EMAIL("email",3),

    /**
     * 银行卡
     */
    BANK_CARD("bankCard",4),

    /**
     * 地址
     */
    ADDRESS("address",5),

    /**
     * 姓名
     */
    NAME("name",6),

    /**
     * 密码
     */
    PASSWORD("password",7)
    ;

    private final String desc;

    private final Integer type;

}
