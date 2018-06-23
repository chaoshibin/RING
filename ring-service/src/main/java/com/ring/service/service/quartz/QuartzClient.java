package com.ring.service.service.quartz;

import com.ring.common.exception.BusinessException;
import com.ring.common.util.ClassUtil;
import com.ring.core.util.SchedulerUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
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





}
