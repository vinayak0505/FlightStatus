package com.vinayak.flight_status.api.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.notification.NotificationService;
import com.vinayak.flight_status.api.notification.SaveMessagingTokenData;
import com.vinayak.flight_status.api.ticket.SaveTicketToNotificationData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private static final String GROUP_ID = "flight1";
    private final NotificationService notificationService;

    @KafkaListener(topics = KafkaHeader.FLIGHT_MESSAGING_TOKEN_DATA, groupId = GROUP_ID)
    public void consumeSaveMessagingTokenData(SaveMessagingTokenData messagingTokenData) {
        System.out.println("Consumed message consumeSaveMessagingTokenData: " + messagingTokenData.toString());
        notificationService.saveMessagingToken(messagingTokenData.getUserId(), messagingTokenData.getFirebaseToken());
    }

    @KafkaListener(topics = KafkaHeader.FLIGHT_TICKET_NOTIFICATION, groupId = GROUP_ID)
    public void consumeSaveTicketToNotification(SaveTicketToNotificationData messagingTokenData) {
        System.out.println("Consumed message consumeSaveTicketToNotification: " + messagingTokenData.toString());
        notificationService.saveTicketToNotification(messagingTokenData.getUserId(), messagingTokenData.getFlightId());
    }
}
