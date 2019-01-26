package com.ring.core.lock;

/**
 * Copyright (C), 2019-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2019/1/22 17:00
 * Description:
 */
public interface Lock {

    /**
     * 加锁
     *
     * @param key
     * @return
     */
    boolean lock(String key);


    /**
     * 解锁
     *
     * @param key
     */
    void unlock(String key);
}
