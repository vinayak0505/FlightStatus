package com.vinayak.flight_status.api.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.notification.NotificationService;
import com.vinayak.flight_status.api.notification.SaveMessagingTokenData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "flight-messaging-token-data", groupId = "flight1")
    public void consumeSaveMessagingTokenData(SaveMessagingTokenData messagingTokenData) {
        notificationService.saveMessagingToken(messagingTokenData.getUserId(), messagingTokenData.getFirebaseToken());
    }
}
