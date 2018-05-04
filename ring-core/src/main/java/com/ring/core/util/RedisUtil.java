package com.ring.core.util;

import com.google.common.base.Charsets;
import com.google.common.primitives.Longs;
import com.ring.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/4/23
 * @author CHAO 修改日期：2018/4/23
 * @version 1.0.0
 * @since 1.0.0
 */
public final class RedisUtil {

    private final static StringRedisTemplate redisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
    private ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    private static final long DEFAULT_EXPIRED = 2 * 60 * 1000L;

    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    /**
     * 生成20位数字型单号
     *
     * @param key
     * @return
     */
    public static String generateSerialNo(String key) {
        return generateSerialNo(key, 20);
    }

    /**
     * 生成数字型单号
     *
     * @param key  redis键
     * @param size 长度
     * @return
     */
    public static String generateSerialNo(String key, Integer size) {
        String prefix = key + "-" + "currentDate";
        String todayStr = DateFormatUtils.format(new Date(), "yyyyMMdd");
        if (!todayStr.equals(redisTemplate.opsForValue().get(prefix))) {
            redisTemplate.opsForValue().set(prefix, todayStr);
            redisTemplate.delete(key);
        }
        Long sequence = redisTemplate.opsForValue().increment(key, 1L);
        String extendSequence = StringUtils.leftPad(String.valueOf(sequence), size - 8, '0');
        String serialNo = todayStr + extendSequence;
        return serialNo;
    }

    public static boolean tryGetDistributedLock(String key) {
        return tryGetDistributedLock(key, DEFAULT_EXPIRED);
    }

    /**
     * @param key     锁的键值
     * @param expired 有效时间（从当前时间持续毫秒数）
     * @return
     */
    public static Boolean tryGetDistributedLock(String key, long expired) {
        Boolean lock = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                //失效时间
                long invalidTime = System.currentTimeMillis() + expired;
                byte[] keyByte = key.getBytes(Charsets.UTF_8);
                /**
                 * value值（失效时间戳）作为死锁检验
                 */
                byte[] valueByte = Longs.toByteArray(invalidTime);
                //在setNx之前获取时间戳用于检验是否死锁，更具准确性
                long currentTimeMillis = System.currentTimeMillis();
                if (redisConnection.setNX(key.getBytes(Charsets.UTF_8), valueByte)) {
                    redisConnection.pExpire(keyByte, expired);
                } else {
                    /**
                     * 1.上面使用pExpire设置了失效时间，进入此处说明pExpire方法未执行（在expired < 定时任务间隔的前提下）
                     * 2.redis为单线程操作 取锁失败说明之前有线程成功获取锁
                     * 3.根据value判断是否失效---防止死锁
                     * 4.上一次设置的值oldValueByte
                     */
                    byte[] oldValueByte = redisConnection.get(keyByte);
                    long oldValueLong = Longs.fromByteArray(oldValueByte);
                    if (oldValueLong < currentTimeMillis) {
                        /**
                         * 上一次设置的值小于此次请求锁的时间，说明锁已失效
                         * 重新设值并取回上次设置值的时间，oldValueByte与getSetValueByte为两次取得的时间戳字节
                         * 如果两个字节数组相等说明成功获得锁，否则意味着在两次请求锁期间有第三方线程成功请求锁
                         */
                        byte[] getSetValueByte = redisConnection.getSet(keyByte, valueByte);
                        if (Arrays.equals(oldValueByte, getSetValueByte)) {
                            redisConnection.pExpire(keyByte, expired);
                            return Boolean.TRUE;
                        }
                    }
                    return Boolean.FALSE;
                }
                return Boolean.TRUE;
            }
        });
        return lock;
    }


    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return JsonUtil.toJSON(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return JsonUtil.fromJSON(json, clazz);
    }
}
