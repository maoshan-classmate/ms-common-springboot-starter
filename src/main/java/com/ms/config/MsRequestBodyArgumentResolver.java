package com.ms.config;

import com.ms.annotation.MsRequestBody;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author maoshan-classmate
 * @date 2024/9/2 21:35
 */
public class MsRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {


    /**
     * 指明MsRequestBodyArgumentResolver解析器只处理带有@MsRequestBody注解的参数
     * @param parameter 方法参数
     * @return true表示支持，false表示不支持
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(MsRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return null;
    }
}
