package com.ring.core.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;


/**
 * @author chaoshibin
 */
@ConfigurationProperties(prefix = "framework.http")
public class RestTemplateConfig {

    private int socketTimeout = 10000;
    private int connectTimeout = 10000;
    /**
     * 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，等待时间过长将是灾难性的
     */
    private int connectionRequestTimeout = 200;
    /**
     * 最大并发
     */
    private int maxTotal = 1000;
    /**
     * 同路由的并发数
     */
    private int defaultMaxPerRoute = new Double(maxTotal * 0.8).intValue();

    @Bean
    @ConditionalOnMissingBean({RestOperations.class, RestTemplate.class})
    public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        /**
         * StringHttpMessageConverter 默认使用ISO-8859-1编码，此处修改为UTF-8
         */
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(Charset.forName("UTF-8"));
            }
        }
        return restTemplate;
    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
        /**
         * Spring使用；两种方式实现http请求
         * 1.SimpleClientHttpRequestFactory，使用J2SE提供的方式（既java.net包提供的方式）创建底层的Http请求连接
         * 2.HttpComponentsClientHttpRequestFactory，底层使用HttpClient访问远程的Http服务，使用HttpClient可以配置连接池和证书等信息
         */
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    @Bean
    @ConditionalOnMissingBean({HttpClient.class})
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(maxTotal);
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                //读取超时时间
                .setSocketTimeout(socketTimeout)
                //连接超时时间
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();

        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                //设置重试次数，默认三次未开启
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, true))
                .build();
    }
}
