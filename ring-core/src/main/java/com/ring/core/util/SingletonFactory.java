package com.ring.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (C), 2019-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2019/1/25 9:51
 * Description:
 */
public final class SingletonFactory {

    private static Map<Class<?>, Object> beanFactory = new ConcurrentHashMap<>();

    private SingletonFactory() {

    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> clazz) {

        T instance = (T) beanFactory.get(clazz);
        if (instance == null) {
            synchronized (SingletonFactory.class) {
                instance = (T) beanFactory.get(clazz);
                if (instance == null) {
                    try {
                        instance = clazz.newInstance();
                        beanFactory.put(clazz, instance);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return instance;
    }
}
