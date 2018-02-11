package com.ring.core.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author chaoshibin
 */
//@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.jrquser.queue}")
    private String message;

    @Value("${rabbitmq.jrquser.routing}")
    private String routing;

    @Value("${rabbitmq.jrquser.exchange.direct}")
    private String exchange;

    @Bean
    public Queue queueMessage() {
        return new Queue(this.message);
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
}
