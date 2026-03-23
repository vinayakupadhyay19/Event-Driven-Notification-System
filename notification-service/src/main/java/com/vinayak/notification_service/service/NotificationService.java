package com.vinayak.notification_service.service;

import org.springframework.stereotype.Service;

import com.vinayak.common.dto.NotificationEvent;
import com.vinayak.notification_service.producer.KafkaProducerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaProducerService producerService;

    public void processNotification(NotificationEvent event) {

        if (event.getType() == null) {
            throw new RuntimeException("Notification type is missing");
        }

        switch (event.getType()) {

            case EMAIL:
                producerService.sendToTopic("email-topic", event);
                break;

            case SMS:
                producerService.sendToTopic("sms-topic", event);
                break;

            case PUSH:
                producerService.sendToTopic("push-topic", event);
                break;

            default:
                throw new RuntimeException("Unsupported notification type");
        }
    }
}
