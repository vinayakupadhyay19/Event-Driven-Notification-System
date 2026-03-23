package com.vinayak.notification_service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.vinayak.common.dto.NotificationEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    public void sendToTopic(String topic, NotificationEvent event) {
        kafkaTemplate.send(topic, event);
        System.out.println("Sent to topic: " + topic + " | Event: " + event);
    }
}
