package com.vinayak.notification_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic emailTopic() {
		return new NewTopic("email-topic", 3, (short) 1);
	}

	@Bean
	public NewTopic smsTopic() {
		return new NewTopic("sms-topic", 3, (short) 1);
	}

	@Bean
	public NewTopic pushTopic() {
		return new NewTopic("push-topic", 3, (short) 1);
	}
}

