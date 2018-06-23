package com.ring.core.config.quartz;

import com.ring.core.util.SpringContextHolder;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2018/6/23
 * @author chaoshibin 修改日期：2018/6/23
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class SpringJobFactroy extends AdaptableJobFactory {
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        return SpringContextHolder.getBean(bundle.getJobDetail().getJobClass());
    }
}
