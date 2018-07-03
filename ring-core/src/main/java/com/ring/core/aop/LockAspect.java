package com.ring.core.aop;

import com.ring.core.annotion.Lockable;
import com.ring.core.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class LockAspect {

    public static final int MILLIS = 1000;

    private final static Map<String, String> DUPLICATE_KEY_MAP = new ConcurrentHashMap<>();

    @Around("@annotation(com.ring.core.annotion.Lockable)")
    public Object distributeLock(ProceedingJoinPoint pjp) {

        Object result = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        Lockable lockable = method.getAnnotation(Lockable.class);
        this.checkLockable(lockable);
        String lockKey = lockable.key();
        if (StringUtils.isBlank(lockKey)){
            lockKey = methodSignature.toLongString().split(" ")[2].replace(".", "/");
        }
        this.checkDuplicateKey(lockKey, methodSignature.toLongString());
        if (!RedisUtil.tryGetDistributedLock(lockKey, lockable.expireSeconds() * MILLIS)) {
            log.warn("获取分布式锁失败，任务退出");
            return result;
        }
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            log.error("分布式锁切面异常", throwable);
            throw new RuntimeException("分布式锁切面异常");
        }
        return result;
    }

    private void checkLockable(Lockable lockable) {
        if (lockable.expireSeconds() < 1) {
            throw new RuntimeException("不合法的expireSeconds");
        }
    }

    private void checkDuplicateKey(String lockKey, String method) {
        if (DUPLICATE_KEY_MAP.containsKey(lockKey)) {
            if (!DUPLICATE_KEY_MAP.get(lockKey).equals(method)) {
                log.error("使用了重复的Lock key : {}", lockKey);
                throw new RuntimeException("duplicate lock key:" + lockKey);
            }
        } else {
            DUPLICATE_KEY_MAP.put(lockKey, method);
        }
    }
}
