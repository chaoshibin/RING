package com.ring.core.aop;

import com.ring.core.annotion.Lockable;
import com.ring.core.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/14
 * @author CHAO 修改日期：2018/6/14
 * @version 1.0.0
 * @since 1.0.0
 */
@Aspect
@Slf4j
public class LockAspect {

    public static final int MILLIS = 1000;

    private final static Map<String, String> DUPLICATE_KEY_MAP = new ConcurrentHashMap<>();

    @Around("@annotation(com.ring.core.annotion.Lockable)")
    public Object distributeLock(ProceedingJoinPoint pjp) {

        Object resultObject = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();

        Lockable lockable = targetMethod.getAnnotation(Lockable.class);
        String key = lockable.key();
        long expireSeconds = lockable.expireSeconds();

        if (StringUtils.isBlank(key)) {
            throw new RuntimeException("不合法的Lock key");
        }

        if (expireSeconds < 1) {
            throw new RuntimeException("不合法的expireSeconds");
        }
        String methodName = targetMethod.getName();
        if (DUPLICATE_KEY_MAP.containsKey(methodName)) {
            if (!DUPLICATE_KEY_MAP.get(methodName).equals(key)) {
                log.error("使用了重复的Lock key : {}", key);
                throw new RuntimeException("duplicate lock key:" + key);
            }
        } else {
            DUPLICATE_KEY_MAP.put(methodName, key);
        }

        if (!RedisUtil.tryGetDistributedLock(key, expireSeconds * MILLIS)) {
            log.warn("获取分布式锁失败，任务退出");
            return resultObject;
        }
        try {
            resultObject = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("分布式锁切面异常", throwable);
        }

        return resultObject;
    }

}
