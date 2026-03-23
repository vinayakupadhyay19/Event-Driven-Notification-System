package com.vinayak.user_service.service;



import java.util.UUID;

import org.springframework.stereotype.Service;

import com.vinayak.common.dto.NotificationEvent;
import com.vinayak.common.enums.NotificationType;
import com.vinayak.user_service.client.NotificationClient;
import com.vinayak.user_service.dto.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final NotificationClient notificationClient;

    public void registerUser(User user) {

        // 1. Save user (DB later)
        System.out.println("User saved: " + user);

        // 2. Create Notification Event
        NotificationEvent event = new NotificationEvent(
        	    UUID.randomUUID().toString(),  // String
        	    user.getId(),                  // maybe UUID or String
        	    user.getEmail(),
        	    user.getPhoneNumber(),
        	    "Welcome " + user.getName() + "!",
        	    NotificationType.EMAIL,
        	    "PENDING",
        	    System.currentTimeMillis()
        );

        // 3. CALL notification-service via FEIGN
        String response = notificationClient.sendNotification(event);

        System.out.println("Notification response: " + response);
    }
}