package com.ring.common.util;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/6/23
 * @author chaoshibin 修改日期：2018/6/23
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public final class ClassUtil {
    private ClassUtil() {

    }

    public static <T> Class<? extends T> loadClass(String className, Class<T> clazz) {
        Class<? extends T> subclass = null;
        try {
            subclass = Class.forName(className).asSubclass(clazz);
        } catch (ClassNotFoundException e) {
            log.error("load class failure", e);
            throw new RuntimeException(e);
        }
        return subclass;
    }
}
