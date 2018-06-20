package com.ring.core.config.quartz;

import com.ring.core.mybatis.MyBatisAutoConfiguration;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/20
 * @author CHAO 修改日期：2018/6/20
 * @version 1.0.0
 * @since 1.0.0
 */
//@Configuration
@AutoConfigureAfter(MyBatisAutoConfiguration.class)
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean){
        return schedulerFactoryBean.getScheduler();
    }
}
