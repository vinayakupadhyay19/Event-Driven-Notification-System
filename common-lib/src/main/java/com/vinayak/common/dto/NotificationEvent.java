package com.vinayak.common.dto;

import com.vinayak.common.enums.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor   // ✅ THIS FIXES YOUR ERROR
@NoArgsConstructor
public class NotificationEvent {

    private String eventId;
    private String userId;
    private String email;
    private String phoneNumber;
    private String message;
    private NotificationType type;
    private String status;
    private long timestamp;
}