package com.ms.custom;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author maoshan-classmate
 * @date 2025/1/2 9:51
 * @version 1.0
 */
public interface MsCustomLockKeyProvider {

    /**
     * 获取lockKey
     *
     * @param proceedingJoinPoint 切点
     * @return lockKey
     */
    String getLockKey(ProceedingJoinPoint proceedingJoinPoint);
}
