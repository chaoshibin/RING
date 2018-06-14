package com.ring.service.amqp;

import cn.hutool.log.StaticLog;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * @author chaoshibin
 */
public class DirectRabbitHandler {

    @RabbitListener(queues = "${rabbitmq.jrquser.queue}")
    public void process(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        byte[] body = message.getBody();
        if (body == null || body.length == 0) {
            StaticLog.info("修改手机号消息体为空");
            return;
        }
    }
}
