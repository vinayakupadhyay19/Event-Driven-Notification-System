package com.vinayak.user_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.vinayak.common.dto.NotificationEvent;

@FeignClient(name = "notification-service", url = "http://localhost:8082")
public interface NotificationClient {

    @PostMapping("/notifications")
    String sendNotification(NotificationEvent event);
}