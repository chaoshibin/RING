package com.ring.core.config.quartz;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/20
 * @author CHAO 修改日期：2018/6/20
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class QuartzConfig {

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactory = new PropertiesFactoryBean();
        propertiesFactory.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactory.afterPropertiesSet();
        return propertiesFactory.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(SpringJobFactory springJobFactroy,
                                                     @Qualifier("quartzProperties")Properties properties) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(springJobFactroy);
        factoryBean.setQuartzProperties(properties);
        return factoryBean;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factoryBean) {
        return factoryBean.getScheduler();
    }
}
