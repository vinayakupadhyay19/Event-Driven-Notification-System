package com.vinayak.user_service.kafka;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.vinayak.common.dto.NotificationEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    private static final String TOPIC = "notification-topic";

    public void sendNotification(NotificationEvent event) {
        kafkaTemplate.send(TOPIC, event);
    }
}