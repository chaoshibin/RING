package com.ring.core.config.quartz;

import org.quartz.JobDataMap;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/6/23
 * @author chaoshibin 修改日期：2018/6/23
 * @version 1.0.0
 * @since 1.0.0
 */
public class SchedulerJob {
    private String className;
    private String jobName;
    private String jobGroup;
    private String cronExpression;
    private JobDataMap jobDataMap;
    private JobDataMap triggerDataMap;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public JobDataMap getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(JobDataMap jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    public JobDataMap getTriggerDataMap() {
        return triggerDataMap;
    }

    public void setTriggerDataMap(JobDataMap triggerDataMap) {
        this.triggerDataMap = triggerDataMap;
    }
}
