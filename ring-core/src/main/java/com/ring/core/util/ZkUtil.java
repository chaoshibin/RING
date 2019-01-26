package com.ring.core.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/14
 * @author CHAO 修改日期：2018/6/14
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ZkUtil {
    private final static CuratorFramework ZK_CLIENT = SpringBeanFactory.get(CuratorFramework.class);

    public boolean sharedLock(String path) {
        ZK_CLIENT.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL);
                //.forPath(path);
        return false;
    }

}
