package com.vinayak.email_service.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import com.vinayak.common.dto.NotificationEvent;
@Configuration
public class KafkaErrorConfig {
	
	
	// ✅ ConsumerFactory (needed again)
    @Bean
    public ConsumerFactory<String, NotificationEvent> consumerFactory() {

        JsonDeserializer<NotificationEvent> deserializer =
                new JsonDeserializer<>(NotificationEvent.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "email-group-v4");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {

        // Send failed messages to DLQ
        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(kafkaTemplate);

        // Retry 3 times with 2 sec delay
        FixedBackOff backOff = new FixedBackOff(2000L, 3);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);

        // ✅ THIS IS THE FIX
        errorHandler.setCommitRecovered(true);

        return errorHandler;
    }
    
    
    
   // ✅ Bind error handler to listener container
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, NotificationEvent> consumerFactory,
            DefaultErrorHandler errorHandler) {

        ConcurrentKafkaListenerContainerFactory<String, NotificationEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);

        // 🔥 THIS LINE WAS MISSING
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}