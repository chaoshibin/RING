package com.ring.common.util;

/**
 * Copyright (C), 2018-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2018/10/29 17:59
 * Description:
 */
public interface RunnableEnhance extends Runnable {

    default void start(){
        new Thread(this).start();
    }
}
