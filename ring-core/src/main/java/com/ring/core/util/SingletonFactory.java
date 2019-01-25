package com.ring.core.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C), 2019-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2019/1/25 9:51
 * Description:
 */
public class SingletonFactory {

    private final static ConcurrentHashMap<Class<?>, Object> BEAN_POOL = new ConcurrentHashMap<>();

    private SingletonFactory() {

    }

    public static <T> T build(Class<T> clazz) {
        T instance = (T) BEAN_POOL.get(clazz);
        if (instance == null) {
            synchronized (SingletonFactory.class) {
                instance = (T) BEAN_POOL.get(clazz);
                if (instance == null) {
                    try {
                        instance = clazz.newInstance();
                        BEAN_POOL.put(clazz, instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
