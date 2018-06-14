package com.ring.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @author chaoshibin
 */
@ConfigurationProperties(prefix = "rabbitmq.default")
public class RabbitConfig {

    private String queue;

    private String routing;

    private String exchange;

    @Bean
    public Queue queueMessage() {
        return new Queue(this.queue);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    /**
     * 交换机绑定队列 生产者发送消息不需指定队列，指定交换机即可
     *
     * @param queueMessage 消息队列
     * @param exchange     交换机
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage(Queue queueMessage, DirectExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(routing);
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }
}
