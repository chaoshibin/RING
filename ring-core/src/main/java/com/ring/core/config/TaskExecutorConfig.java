package com.ring.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/12
 * @author CHAO 修改日期：2018/6/12
 * @version 1.0.0
 * @since 1.0.0
 */
@EnableAsync
@ConfigurationProperties(prefix = "framework.executor")
public class TaskExecutorConfig {

    /**
     * 核心线程数
     */
    private int corePoolSize = 10;
    /**
     * 最大线程数
     */
    private int maxPoolSize = 100;
    /**
     * 缓存队列容量
     */
    private int queueCapacity = 10000;
    /**
     * 存活时间（秒）
     */
    private int keepAliveSeconds = 60;


    /**
     * Spring线程池实现，在需要异步执行的方法添加注解 @Async("localExecutor")即可异步操作
     *
     * @return
     */
    @Bean("localExecutor")
    public Executor localExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("localExecutor-");

        /**
         *rejection-policy：拒绝策略->当pool已经达到max size的时候，如何处理新任务
         *CALLER_RUNS：不在线程池中执行任务，而是由调用者所在的线程来执行
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.initialize();
        return executor;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }
}
