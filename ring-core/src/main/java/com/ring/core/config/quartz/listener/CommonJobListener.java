package com.ring.core.config.quartz.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/20
 * @author CHAO 修改日期：2018/6/20
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public abstract class CommonJobListener implements JobListener {
    private static final String JOB_LISTENER_NAME = CommonJobListener.class.getSimpleName();
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        log.info("任务监听器[{}]jobToBeExecuted执行", JOB_LISTENER_NAME);
        String jobName = jobExecutionContext.getTrigger().getKey().toString();
        log.info(jobName + ":任务开始执行");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        log.info("任务监听器[{}]jobExecutionVetoed执行", JOB_LISTENER_NAME);
        String jobName = jobExecutionContext.getTrigger().getKey().toString();
        log.info(jobName + ":任务被否决");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        log.info("任务监听器[{}]jobWasExecuted执行", JOB_LISTENER_NAME);
        String jobName = jobExecutionContext.getTrigger().getKey().toString();
        log.info(jobName + ":任务执行完毕");
    }
}
