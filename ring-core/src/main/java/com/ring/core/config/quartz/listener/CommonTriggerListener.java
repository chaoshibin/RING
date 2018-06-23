package com.ring.core.config.quartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
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
public class CommonTriggerListener implements TriggerListener {
    private static final String TRIGGER_LISTENER_NAME = CommonTriggerListener.class.getSimpleName();

    @Override
    public String getName() {
        return TRIGGER_LISTENER_NAME;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        log.info("触发器监听器[{}]triggerFired执行", TRIGGER_LISTENER_NAME);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        log.info("触发器监听器[{}]vetoJobExecution执行", TRIGGER_LISTENER_NAME);
        //TODO 分布式锁否决任务
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("触发器监听器[{}]triggerMisfired执行", TRIGGER_LISTENER_NAME);
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        log.info("触发器监听器[{}]triggerComplete执行", TRIGGER_LISTENER_NAME);
    }
}
