package com.ring.core.aop;

import com.ring.common.exception.LockException;
import com.ring.core.annotion.Lock;
import com.ring.core.annotion.Lockable;
import com.ring.core.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

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
    @Autowired
    private RedissonClient redissonClient;

    //@Around("@annotation(com.ring.core.annotion.Lockable)")
    public Object distributeLock(ProceedingJoinPoint pjp) {

        Object result = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        Lockable lockable = method.getAnnotation(Lockable.class);
        this.checkLockable(lockable);
        String lockKey = lockable.key();
        if (StringUtils.isBlank(lockKey)) {
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

    @Around("@annotation(com.ring.core.annotion.Lock)")
    public Object tryLock(ProceedingJoinPoint pjp) throws Throwable {

        Object result = null;
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Lock lockAnnotation = method.getAnnotation(Lock.class);
        String uniqueKey = getUniqueKey(lockAnnotation.unique(), methodSignature, pjp.getArgs());
        String keyPrefix = lockAnnotation.prefix();

        String lockKey = keyPrefix + ":" + uniqueKey;
        RLock lock = redissonClient.getLock(lockKey);
        if (!lock.tryLock()) {
            log.error("获取分布式锁失败，任务退出, key = {}", lockKey);
            throw new LockException("获取分布式锁失败，key=" + lockKey);
        }
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            lock.unlock();
        }
        return result;
    }


    /**
     * 获取缓存的key
     * key 定义在注解上，支持SPEL表达式
     *
     * @return
     */
    private String getUniqueKey(String uniqueKey, MethodSignature method, Object[] args) {
        String[] paraNameArr = method.getParameterNames();
        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //创建SPEL上下文
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            evaluationContext.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(uniqueKey).getValue(evaluationContext, String.class);
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
