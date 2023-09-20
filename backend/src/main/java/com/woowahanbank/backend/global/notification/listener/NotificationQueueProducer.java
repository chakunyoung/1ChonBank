package com.woowahanbank.backend.global.notification.listener;

import com.woowahanbank.backend.global.notification.event.NotificationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationQueueProducer {
    private final RabbitTemplate rabbitTemplate;
    @Value("${spring.rabbitmq.exchange-name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    @EventListener
    public void handleCustomEvent(NotificationEvent event) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, event);
    }
}
