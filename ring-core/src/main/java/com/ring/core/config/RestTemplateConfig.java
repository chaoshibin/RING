package com.ring.core.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * http keepAlive
 *
 * @author chaoshibin
 */
@Slf4j
public class RestTemplateConfig {

    @Bean
    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
    public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        List<HttpMessageConverter<?>> converters = Lists.newArrayListWithCapacity(2);
        //StringHttpMessageConverter 默认使用ISO-8859-1编码，此处修改为UTF-8
        //HttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        //converters.add(stringHttpMessageConverter);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //设置中文编码格式
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(mappingJackson2HttpMessageConverter);
        //覆盖默认的文本转换器
        restTemplate.setMessageConverters(converters);
        log.debug("初始化RestTemplate");
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        /*
         * Spring使用；两种方式实现http请求
         * 1.SimpleClientHttpRequestFactory，使用J2SE提供的方式（既java.net包提供的方式）创建底层的Http请求连接
         * 2.HttpComponentsClientHttpRequestFactory，底层使用HttpClient访问远程的Http服务，使用HttpClient可以配置连接池和证书等信息
         */
        log.debug("初始化ClientHttpRequestFactory");
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    @ConditionalOnMissingBean({HttpClient.class})
    public HttpClient httpClient(RestTemplateProperties properties) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(properties.getMaxTotal());
        connectionManager.setDefaultMaxPerRoute(properties.getDefaultMaxPerRoute());
        RequestConfig requestConfig = RequestConfig.custom()
                //读取超时时间
                .setSocketTimeout(properties.getSocketTimeoutMs())
                //连接超时时间
                .setConnectTimeout(properties.getConnectTimeoutMs())
                .setConnectionRequestTimeout(properties.getConnectionRequestTimeoutMs())
                .build();

        log.debug("初始化HttpClient");
        return HttpClientBuilder.create()
                //开启后台线程定时清理无效连接
                .evictIdleConnections(properties.getMaxIdleTimeMs(), TimeUnit.MILLISECONDS)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                //设置HTTP Keep-Alive
                .setKeepAliveStrategy((response, context) -> {
                    Assert.notNull(response, "HTTP response");
                    final HeaderElementIterator it = new BasicHeaderElementIterator(
                            response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                    while (it.hasNext()) {
                        final HeaderElement he = it.nextElement();
                        final String param = he.getName();
                        final String value = he.getValue();
                        if (value != null && "timeout".equalsIgnoreCase(param)) {
                            try {
                                return Long.parseLong(value) * 1000;
                            } catch (final NumberFormatException ignore) {
                            }
                        }
                    }
                    //自定义Keep-Alive
                    return properties.getKeepAlive();
                })
                //设置重试次数，默认三次未开启
                //.setRetryHandler(new DefaultHttpRequestRetryHandler(3, false))
                .build();
    }

    @Getter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @Configuration
    @ConfigurationProperties(prefix = "framework.http")
    public static class RestTemplateProperties {

        /**
         * 读取超时时间
         */
        private int socketTimeoutMs = 10 * 1000;

        /**
         * 连接超时时间
         */
        private int connectTimeoutMs = 3 * 1000;

        /**
         * 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，等待时间过长将是灾难性的
         */
        private int connectionRequestTimeoutMs = 200;

        /**
         * 最大并发
         */
        private int maxTotal = 800;

        /**
         * 同路由的最大并发数
         */
        private int defaultMaxPerRoute = new Double(maxTotal * 0.3).intValue();

        /**
         * 最大空闲时间
         */
        private int maxIdleTimeMs = 60 * 1000;

        /**
         * http keep-Alive
         */
        private int keepAlive = 60 * 1000;
    }
}
