package com.ms.handler;


import cn.hutool.core.util.ClassUtil;
import com.alibaba.fastjson.JSONObject;
import com.ms.annotation.MsSingleRequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

/**
 * @author maoshan-classmate
 * @date 2024/9/7 14:36
 */
public class MsRequestBodyHandler implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_BUFFER_SIZE = 1024;

    private static final Logger logger = LoggerFactory.getLogger(MsRequestBodyHandler.class);

    private static final String CURLY_BRACKET_OPEN = "{";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MsSingleRequestBody.class) && Objects.requireNonNull(parameter.getParameterAnnotation(MsSingleRequestBody.class)).required();
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        MsSingleRequestBody msRequestBody = parameter.getParameterAnnotation(MsSingleRequestBody.class);
        String name = msRequestBody != null ? parameter.getParameterName() : "";
        Class<?> parameterType = parameter.getParameterType();
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String stringBuilder;
        try {
            assert request != null;
            try(BufferedReader reader = request.getReader()) {
                stringBuilder= readData(reader);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        if (stringBuilder.startsWith(CURLY_BRACKET_OPEN)){
            if(ClassUtil.isPrimitiveWrapper(parameterType) || ClassUtil.isBasicType(parameterType) || String.class.equals(parameterType)){
                JSONObject jsonObject = JSONObject.parseObject(stringBuilder);
                return jsonObject.getObject(name, parameterType);
            }else {
                throw new IllegalArgumentException("@MsRequestBody 只支持基本数据类型、包装类型、字符串类型");
            }
        }else{
            throw new IllegalArgumentException("@MsRequestBody 只支持json格式");
        }
    }

    /**
     * 读取请求数据
     *
     * @param reader 请求数据
     * @return 请求数据字符串
     */
    private String readData(Reader reader) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            char[] buf = new char[DEFAULT_BUFFER_SIZE];
            int rd;
            // 循环读取数据直到结束
            while ((rd = bufferedReader.read(buf)) != -1) {
                // 将读取的数据追加到 StringBuilder 中
                stringBuilder.append(buf, 0, rd);
            }
        } catch (IOException e) {
            logger.error("读取数据失败", e);
        }
        return stringBuilder.toString();
    }
}
