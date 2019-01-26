package com.ring.core.lock;

import com.ring.core.util.SpringBeanFactory;
import org.redisson.api.RedissonClient;

/**
 * Copyright (C), 2019-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2019/1/22 17:02
 * Description:
 */
public class RedisLock implements Lock {

    private RedissonClient client = SpringBeanFactory.get(RedissonClient.class);

    @Override
    public boolean lock(String key) {
        return client.getLock(key).tryLock();
    }

    @Override
    public void unlock(String key) {
        client.getLock(key).unlock();
    }
}
