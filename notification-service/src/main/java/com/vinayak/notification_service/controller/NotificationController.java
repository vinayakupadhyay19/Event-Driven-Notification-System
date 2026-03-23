package com.vinayak.notification_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.common.dto.NotificationEvent;
import com.vinayak.notification_service.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationEvent event) {

        notificationService.processNotification(event);

        return ResponseEntity.ok("Notification routed successfully");
    }
}