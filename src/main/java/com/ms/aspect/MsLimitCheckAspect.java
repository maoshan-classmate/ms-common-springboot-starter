package com.ms.aspect;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import com.ms.annotation.MsLimitCheck;
import com.ms.custom.MsCustomLockKeyProvider;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maoshan-classmate
 * @date 2025/1/1 13:09
 */
@Component
@Aspect
public class MsLimitCheckAspect {

    @Resource
    private ApplicationContext applicationContext;

    Map<String, Cache<String, AtomicInteger>> cacheMap = Maps.newHashMap();


    @Around("@annotation(com.ms.annotation.MsLimitCheck)")
    public Object process(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        method.setAccessible(true);
        MsLimitCheck msLimitCheck = AnnotationUtil.getAnnotation(method, MsLimitCheck.class);
        if (msLimitCheck == null) {
            return proceedingJoinPoint.proceed();
        }
        // 判断是否自定义lockKey获取逻辑
        Class<? extends MsCustomLockKeyProvider> customLockKeyProvider = getCustomLockKeyProvider(proceedingJoinPoint);
        MsCustomLockKeyProvider msCustomLockKeyProvider;
        String lockKey;
        if (customLockKeyProvider != null && !customLockKeyProvider.isInterface()) {
            msCustomLockKeyProvider = applicationContext.getBean(customLockKeyProvider);
            lockKey = msCustomLockKeyProvider.getLockKey(proceedingJoinPoint);
        } else {
            lockKey = null;
        }
        if (CharSequenceUtil.isEmpty(lockKey)) {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                lockKey = ServletUtil.getClientIP(request);
            } else {
                lockKey = "127.0.0.1";
            }
        }
        Cache<String, AtomicInteger> localCache = cacheMap.get(lockKey + ":" + method.getName() + ":" + msLimitCheck.expireCache() + ":" + msLimitCheck.count());
        // 若缓存不存在新增一个缓存
        if (localCache == null) {
            localCache = CacheBuilder.newBuilder().expireAfterAccess(msLimitCheck.expireCache(), msLimitCheck.timeUnit()).maximumSize(1000).build();
            cacheMap.put(lockKey + ":" + method.getName() + ":" + msLimitCheck.expireCache() + ":" + msLimitCheck.count(), localCache);
        }
        AtomicInteger atomicInteger = localCache.get(lockKey, () -> new AtomicInteger(0));
        atomicInteger.incrementAndGet();
        if (atomicInteger.intValue() > msLimitCheck.count()) {
            throw new RuntimeException(msLimitCheck.errorMessage());
        }
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取自定义控制粒度
     *
     * @param joinPoint 切点
     * @return lockKey
     */
    private Class<? extends MsCustomLockKeyProvider> getCustomLockKeyProvider(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        MsLimitCheck msLimitCheck = method.getAnnotation(MsLimitCheck.class);
        return msLimitCheck.customLockKeyProvider();
    }
}
