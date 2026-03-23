package com.vinayak.email_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vinayak.common.dto.NotificationEvent;
import com.vinayak.email_service.service.EmailService;

@Service
public class EmailConsumer {
	
	private int counterRetry = 0;

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
    	    topics = "email-topic",
    	    containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(NotificationEvent event) {
    	
    	counterRetry++;

        System.out.println("Processing: " + counterRetry + "   "+ event);

        // 🔥 Simulate failure (for testing DLQ)
        if (event.getEmail().contains("fail")) {
            throw new RuntimeException("Simulated failure");
        }

        emailService.sendEmail(event.getEmail(), event.getMessage());

        System.out.println("Email Sent!");
    }
}