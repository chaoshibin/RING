package com.ring.core.util;

import com.ring.common.exception.BusinessException;
import com.ring.common.util.ClassUtil;
import com.ring.core.config.quartz.SchedulerJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.Set;

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
public final class SchedulerUtil {

    private final static Scheduler SCHEDULER = SpringContextHolder.getBean(Scheduler.class);

    public void createJob(SchedulerJob job) {
        if (SchedulerUtil.schedulerShutDown()) {
            log.info("调度器未启用，任务添加失败");
            throw new BusinessException("调度器未启用，任务添加失败");
        }
        if (SchedulerUtil.jobExists(job.getJobName(), job.getJobGroup())) {
            throw new BusinessException("任务已存在，添加失败");
        }

        Class<? extends Job> clazz = ClassUtil.loadClass(job.getClassName(), Job.class);

        JobBuilder jobBuilder = JobBuilder.newJob(clazz).withIdentity(job.getJobName(), job.getJobGroup());
        if (MapUtils.isNotEmpty(job.getJobDataMap())) {
            jobBuilder.usingJobData(job.getJobDataMap());
        }

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule(job.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withSchedule(cronScheduleBuilder)
                .startNow();
        if (MapUtils.isNotEmpty(job.getTriggerDataMap())) {
            triggerBuilder.usingJobData(job.getJobDataMap());
        }

        SchedulerUtil.createJob(jobBuilder.build(), triggerBuilder.build());
    }

    public static void pauseJob(String jobName, String jobGroup) {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            SCHEDULER.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.info("pauseJob failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void rescheduleJob(String jobName, String jobGroup, String cronExpression) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        rescheduleJob(triggerKey, cronTrigger);
    }

    public static void rescheduleJob(TriggerKey triggerKey, Trigger trigger) {
        try {
            SCHEDULER.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.info("rescheduleJob failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void triggerJob(String name, String group, JobDataMap jobDataMap) {
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            SCHEDULER.triggerJob(jobKey, jobDataMap);
        } catch (SchedulerException e) {
            log.error("trigger job failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void triggerJob(String name, String group) {
        JobKey jobKey = JobKey.jobKey(name, group);
        try {
            SCHEDULER.triggerJob(jobKey);
        } catch (SchedulerException e) {
            log.error("trigger job failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void triggerJob(JobKey jobKey, JobDataMap jobDataMap) {
        try {
            SCHEDULER.triggerJob(jobKey, jobDataMap);
        } catch (SchedulerException e) {
            log.error("trigger job failure", e);
            throw new RuntimeException(e);
        }
    }

    public static JobDetail getJobDetail(JobKey jobKey) {
        try {
            return SCHEDULER.getJobDetail(jobKey);
        } catch (SchedulerException e) {
            log.error("get job detail failure", e);
            throw new RuntimeException(e);
        }
    }


    public static void createJob(JobDetail jobDetail, Trigger trigger) {
        try {
            SCHEDULER.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("create job failure", e);
            throw new RuntimeException(e);
        }
    }

    public static boolean jobExists(String jobName, String jobGroup) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        try {
            return SCHEDULER.checkExists(triggerKey);
        } catch (SchedulerException e) {
            log.error("checkExists job failure", e);
            throw new RuntimeException(e);
        }
    }

    public static boolean schedulerShutDown() {
        try {
            return SCHEDULER.isShutdown();
        } catch (SchedulerException e) {
            log.error("任务调度器异常", e);
            throw new RuntimeException(e);
        }
    }

    public static void resumeJob(String name, String group) {
        JobKey key = new JobKey(name, group);
        try {
            SCHEDULER.resumeJob(key);
        } catch (Exception e) {
            log.info("resumeJob failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void deleteAllJob() throws SchedulerException {
        if (!SCHEDULER.isShutdown()) {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = SCHEDULER.getJobKeys(matcher);
            for (JobKey key : jobKeys) {
                SCHEDULER.interrupt(key);
                SCHEDULER.pauseJob(key);
                SCHEDULER.deleteJob(key);
            }
            SCHEDULER.clear();
        }
    }

    public void deleteJob(String jobName, String groupName) {
        try {
            if (!schedulerShutDown()) {
                JobKey key = JobKey.jobKey(jobName, groupName);
                SCHEDULER.interrupt(key);
                SCHEDULER.pauseJob(key);
                SCHEDULER.deleteJob(key);
                SCHEDULER.clear();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
