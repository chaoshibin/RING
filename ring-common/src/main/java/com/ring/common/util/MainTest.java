package com.ring.common.util;

/**
 * Copyright (C), 2018-2019, 深圳市xxx科技有限公司
 *
 * @author: chaoshibin
 * Date:     2018/10/29 18:00
 * Description:
 */
public class MainTest {

    public static class ThreadTest implements RunnableEnhance{
        @Override
        public void run() {
            System.out.println(1);
        }
    }

    public static void main(String[] args) {
        new ThreadTest().start();
    }
}
