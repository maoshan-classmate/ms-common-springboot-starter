package com.ms.enums;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author maoshan-classmate
 * @version 1.0
 * @description 脱敏策略枚举
 * @date 2024/10/15 15:44
 */
@Getter
@AllArgsConstructor
public enum SensitizationTypeEnum {



    /**
     * 自定义
     */
    CUSTOM {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String customHidden;
            if (!enableRegex){
                customHidden = CharSequenceUtil.hide(String.valueOf(content), startInclude,
                        endExclude >= startInclude ? endExclude : content.length() + endExclude);
                jsonGenerator.writeString(customHidden);
            }else{
                customHidden = StrUtil.isBlank(customRegex) || StrUtil.isBlank(customReplace) ?
                        content : Pattern.compile(customRegex).matcher(content).replaceAll(customReplace);
            }
            return customHidden;
        }
    },

    /**
     * 手机号
     */
    MOBILE {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String mobilePhone = DesensitizedUtil.mobilePhone(content);
            jsonGenerator.writeString(mobilePhone);
            return mobilePhone;
        }
    },

    /**
     * 身份证
     */
    ID_CARD {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String idCardNum = DesensitizedUtil.idCardNum(String.valueOf(content), 1, 2);
            jsonGenerator.writeString(idCardNum);
            return idCardNum;
        }
    },

    /**
     * 邮箱
     */
    EMAIL {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String email = DesensitizedUtil.email(String.valueOf(content));
            jsonGenerator.writeString(email);
            return email;
        }
    },

    /**
     * 银行卡
     */
    BANK_CARD {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String bankCard = DesensitizedUtil.bankCard(String.valueOf(content));
            jsonGenerator.writeString(bankCard);
            return bankCard;
        }
    },

    /**
     * 地址
     */
    ADDRESS {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String address = DesensitizedUtil.address(String.valueOf(content), 8);
            jsonGenerator.writeString(address);
            return address;
        }
    },

    /**
     * 姓名
     */
    NAME {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String name = CharSequenceUtil.hide(String.valueOf(content), 1, content.length());
            jsonGenerator.writeString(name);
            return name;
        }
    },

    /**
     * 密码
     */
    PASSWORD {
        @Override
        public String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex,String customRegex,String customReplace) throws IOException {
            String password = DesensitizedUtil.password(String.valueOf(content));
            jsonGenerator.writeString(password);
            return password;
        }
    };

    public abstract String sensitizeDate(String content, int startInclude, int endExclude, String customChar, JsonGenerator jsonGenerator,
                                         boolean enableRegex,String customRegex,String customReplace) throws IOException;


}
