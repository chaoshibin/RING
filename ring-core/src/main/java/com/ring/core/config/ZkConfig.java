package com.ring.core.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 功能描述:
 * <p/>
 *
 * @author CHAO 新增日期：2018/6/14
 * @author CHAO 修改日期：2018/6/14
 * @version 1.0.0
 * @since 1.0.0
 */
public class ZkConfig {

    @Bean
    public CuratorFramework curatorFramework(ZooKeeperkProperties properties) {
        RetryPolicy retryPolicy = new RetryNTimes(Integer.MAX_VALUE, 5000);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                .connectString(properties.getConnectionString())
                .sessionTimeoutMs(properties.getSessionTimeoutMs())
                .connectionTimeoutMs(properties.getConnectionTimeoutMs())
                .maxCloseWaitMs(properties.getMaxCloseWaitMs())
                .namespace(properties.getNamespace())
                //.aclProvider(aclProvider)
                //.authorization(scheme, auth)
                //.defaultData(defaultData)
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        return zkClient;
    }


    @Bean
    @ConfigurationProperties(prefix = "framework.zookeeper")
    public ZooKeeperkProperties zooKeeperkProperties(){
        return new ZooKeeperkProperties();
    }

    public static class ZooKeeperkProperties {
        private String connectionString = "localhost:2181";
        private int sessionTimeoutMs = 60000;
        private int connectionTimeoutMs = 15000;
        private int maxCloseWaitMs = 60000;
        private String namespace = null;

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public int getSessionTimeoutMs() {
            return sessionTimeoutMs;
        }

        public void setSessionTimeoutMs(int sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public int getConnectionTimeoutMs() {
            return connectionTimeoutMs;
        }

        public void setConnectionTimeoutMs(int connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
        }

        public int getMaxCloseWaitMs() {
            return maxCloseWaitMs;
        }

        public void setMaxCloseWaitMs(int maxCloseWaitMs) {
            this.maxCloseWaitMs = maxCloseWaitMs;
        }

        public String getNamespace() {
            return namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }
    }
}
