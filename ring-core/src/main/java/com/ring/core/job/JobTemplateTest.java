package com.ring.core.job;

import com.ring.core.config.quartz.template.JobTemplate;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/6/20
 * @author chaoshibin 修改日期：2018/6/20
 * @version 1.0.0
 * @since 1.0.0
 */
public class JobTemplateTest extends JobTemplate {
    @Override
    protected void run() {
        System.out.println("任务被执行");
    }
}
