package com.ms.enums;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.text.CharSequenceUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
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
        public String sensitizeDate(String content, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(content)){
                return CharSequenceUtil.EMPTY;
            }
            String customHidden;
            if (!enableRegex) {
                customHidden = CharSequenceUtil.replace(content, startInclude,
                        endExclude >= startInclude ? endExclude : content.length() + endExclude, customChar);
            } else {
                customHidden = Pattern.compile(customRegex).matcher(content).replaceAll(Character.toString(customChar));
            }
            jsonGenerator.writeString(customHidden);
            return customHidden;
        }
    },

    /**
     * 手机号
     */
    MOBILE {
        @Override
        public String sensitizeDate(String phone, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(phone)) {
                return CharSequenceUtil.EMPTY;
            }
            if (11 > phone.length()) {
                return phone;
            }
            String mobilePhone = CharSequenceUtil.replace(phone, 3, phone.length() - 4, customChar);
            jsonGenerator.writeString(mobilePhone);
            return mobilePhone;
        }
    },

    /**
     * 身份证
     */
    ID_CARD {
        @Override
        public String sensitizeDate(String idCard, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(idCard)) {
                return CharSequenceUtil.EMPTY;
            }
            //需要截取的长度不能大于身份证号长度
            if (3 > idCard.length()) {
                return idCard;
            }
            String idCardNum = CharSequenceUtil.replace(idCard, 1, idCard.length() - 2, customChar);
            jsonGenerator.writeString(idCardNum);
            return idCardNum;
        }
    },

    /**
     * 邮箱
     */
    EMAIL {
        @Override
        public String sensitizeDate(String email, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(email)) {
                return CharSequenceUtil.EMPTY;
            }
            int index = CharSequenceUtil.indexOf(email, '@');
            if (1 >=index ) {
                return email;
            }
            String sensitizeEmail = CharSequenceUtil.replace(email, 1, index, customChar);
            jsonGenerator.writeString(sensitizeEmail);
            return sensitizeEmail;
        }
    },

    /**
     * 银行卡
     */
    BANK_CARD {
        @Override
        public String sensitizeDate(String bankCardNo, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(bankCardNo)) {
                return CharSequenceUtil.EMPTY;
            }
            bankCardNo = CharSequenceUtil.cleanBlank(bankCardNo);
            if (9 > bankCardNo.length() ) {
                return bankCardNo;
            }
            final int length = bankCardNo.length();
            final int endLength = length % 4 == 0 ? 4 : length % 4;
            final int midLength = length - 4 - endLength;
            final StringBuilder buf = new StringBuilder();
            buf.append(bankCardNo, 0, 4);
            for (int i = 0; i < midLength; ++i) {
                if (i % 4 == 0) {
                    buf.append(CharPool.SPACE);
                }
                buf.append(customChar);
            }
            buf.append(CharPool.SPACE).append(bankCardNo, length - endLength, length);
            String bankCard = buf.toString();
            jsonGenerator.writeString(bankCard);
            return bankCard;
        }
    },

    /**
     * 地址
     */
    ADDRESS {
        @Override
        public String sensitizeDate(String address, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(address)) {
                return CharSequenceUtil.EMPTY;
            }
            int length = address.length();
            if (8 > length){
                return address;
            }
            String sensitizeAddress = CharSequenceUtil.replace(address, length - 8, length, customChar);
            jsonGenerator.writeString(sensitizeAddress);
            return sensitizeAddress;
        }
    },

    /**
     * 姓名
     */
    NAME {
        @Override
        public String sensitizeDate(String name, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(name)){
                return CharSequenceUtil.EMPTY;
            }
            if (1 >= name.length()) {
                return name;
            }
            String sensitizeName = CharSequenceUtil.replace(name, 1, name.length(), customChar);
            jsonGenerator.writeString(sensitizeName);
            return sensitizeName;
        }
    },

    /**
     * 密码
     */
    PASSWORD {
        @Override
        public String sensitizeDate(String password, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                    boolean enableRegex, String customRegex) throws IOException {
            if (CharSequenceUtil.isBlank(password)) {
                return CharSequenceUtil.EMPTY;
            }
            if (1 >= password.length()) {
                return password;
            }
            String sensitizePassword = CharSequenceUtil.repeat(customChar, password.length());
            jsonGenerator.writeString(sensitizePassword);
            return sensitizePassword;
        }
    };

    public abstract String sensitizeDate(String content, int startInclude, int endExclude, char customChar, JsonGenerator jsonGenerator,
                                         boolean enableRegex, String customRegex) throws IOException;


}
