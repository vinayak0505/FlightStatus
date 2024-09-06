package com.vinayak.flight_status.api.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.notification.SaveMessagingTokenData;
import com.vinayak.flight_status.api.ticket.SaveTicketToNotificationData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, SaveMessagingTokenData> kafkaTemplateMessagingTokenData;
    private final KafkaTemplate<String, SaveTicketToNotificationData> kafkaTemplateTicketToNotification;

    public void sendSaveMessagingTokenData(SaveMessagingTokenData messagingTokenData) {
        Message<SaveMessagingTokenData> message = MessageBuilder.withPayload(messagingTokenData)
                .setHeader(KafkaHeaders.TOPIC, KafkaHeader.FLIGHT_MESSAGING_TOKEN_DATA).build();

        kafkaTemplateMessagingTokenData.send(message);
    }

    public void sendSaveTicketToNotification(SaveTicketToNotificationData messagingTokenData) {
        Message<SaveTicketToNotificationData> message = MessageBuilder.withPayload(messagingTokenData)
                .setHeader(KafkaHeaders.TOPIC, KafkaHeader.FLIGHT_TICKET_NOTIFICATION).build();

        kafkaTemplateTicketToNotification.send(message);
    }
}
