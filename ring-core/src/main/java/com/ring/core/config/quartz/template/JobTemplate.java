package com.ring.core.config.quartz.template;

import com.ring.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/6/20
 * @author chaoshibin 修改日期：2018/6/20
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public abstract class JobTemplate implements Job {

    /**
     * 任务主体
     */
    protected abstract void run();

    protected boolean conditional() {
        return true;
    }

    protected void before() {

    }

    protected void complete() {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if (this.conditional()) {
            this.before();
            this.run();
            this.complete();
        } else {
            JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
            log.info("不满足前置条件，任务被过滤->{}", JsonUtil.toJSON(jobKey));
        }
    }
}
