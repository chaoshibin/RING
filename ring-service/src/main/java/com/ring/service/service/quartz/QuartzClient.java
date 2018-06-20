package com.ring.service.service.quartz;

import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.StrUtil;
import com.ring.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
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
public class QuartzClient {
    @Autowired
    private Scheduler scheduler;


    /**
     * 验证Job是否存在
     */
    public boolean jobExists(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }

    public void addJob() {
        try {
            if (scheduler.isShutdown()) {
                log.info("调度器关闭了，任务添加失败");
                throw new BusinessException("调度器关闭了，任务添加失败");
            }
        } catch (SchedulerException e) {
            log.error("任务调度器异常", e);
            throw new BusinessException("任务调度器异常");
        }
        String msg = StrUtil.EMPTY;

        try {
            if (jobExists("", "")) {
                throw new BusinessException("任务已存在，添加失败");
            }
            Class<?> clazz = Class.forName("");
            Class<?> aClass = ClassLoaderUtil.loadClass("");
        } catch (Exception e) {
        }
    }
}
