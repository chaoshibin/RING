package com.ring.core.config.quartz;

import com.ring.core.config.quartz.listener.CommonJobListener;
import com.ring.core.config.quartz.listener.CommonTriggerListener;
import lombok.extern.slf4j.Slf4j;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/6/20
 * @author chaoshibin 修改日期：2018/6/20
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@Slf4j
public class InitializeScheduler implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private CommonTriggerListener triggerListener;
    @Autowired
    private CommonJobListener jobListener;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ListenerManager listenerManager;
        try {
            listenerManager = scheduler.getListenerManager();
        } catch (SchedulerException e) {
            log.error("任务调度器获取监听器管理器异常", e);
            throw new RuntimeException(e);
        }
        listenerManager.addTriggerListener(triggerListener);
        listenerManager.addJobListener(jobListener);

        //TODO 初始化持久化任务
    }
}
