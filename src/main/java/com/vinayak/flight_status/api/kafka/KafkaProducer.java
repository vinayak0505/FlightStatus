package com.vinayak.flight_status.api.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.notification.SaveMessagingTokenData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    
    private final KafkaTemplate<String, SaveMessagingTokenData> kafkaTemplate;

    public void sendSaveMessagingTokenData(SaveMessagingTokenData messagingTokenData) {
        Message<SaveMessagingTokenData> message = MessageBuilder.withPayload(messagingTokenData)
        .setHeader(KafkaHeaders.TOPIC, "flight-messaging-token-data").build();
        
        kafkaTemplate.send(message);
    }
}
