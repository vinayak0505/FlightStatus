package com.vinayak.flight_status.api.messaging;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Message.Builder;
import com.vinayak.flight_status.api.flight.FlightStatus;
import com.vinayak.flight_status.api.notification.NotificationType;

@Service
public class FirebaseService {

    public void subscribe(String topic, List<String> registrationTokens) {
        try {
            FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, topic);
        } catch (FirebaseMessagingException e) {
            System.out.println(e);
        }
    }

    public void unSubscribe(String topic, List<String> registrationTokens) {
        try {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(registrationTokens, topic);
        } catch (FirebaseMessagingException e) {
            System.out.println(e);
        }
    }

    public void sendFlightStatus(NotificationType notificationType,
            String topic, FlightStatus flightStatus, Integer flightId) {
        try {
            Builder messageBuilder = Message.builder()
                    .setTopic(topic)
                    .putData("topic", topic)
                    .putData("type", notificationType.name())
                    .putData("flightId", flightId.toString())
                    .putData("flightStatus", flightStatus.name())
                    .putData("tag", "tag_status_" + flightId.toString());

            if (notificationType == NotificationType.SFLIGHT) {
                String body = "";
                switch (flightStatus) {
                    case ON_TIME ->
                        body = "YAHO! Flight on time. Don't forget to leave on time.";
                    case DELAYED ->
                        body = "WAAAIT! Flight got delayed. Please check updated time before leaving.";
                    case CANCELLED ->
                        body = "NOOO! Flight got cancelled. Don't worry this is on us. You can rebook if you want or get full refund.";
                    default -> {
                    }
                }

                messageBuilder.putData("body", body).putData("title", "Your Flight Status Changed");
            }

            FirebaseMessaging.getInstance().send(messageBuilder.build());
        } catch (FirebaseMessagingException e) {
            System.out.println(e);
        }
    }

    public void sendFlightGate(NotificationType notificationType,
            String topic, String gate, Integer flightId) {
        try {
            Builder messageBuilder = Message.builder()
                    .setTopic(topic)
                    .putData("topic", topic)
                    .putData("type", notificationType.name())
                    .putData("flightId", flightId.toString())
                    .putData("gate", gate)
                    .putData("tag", "tag_gate_" + flightId.toString());

            if (notificationType == NotificationType.SGATE) {
                messageBuilder
                        .putData("body", "Gate changed. Current gate is " + gate + ". Please check new gate number before leaving.")
                        .putData("title", "Your Boarding Gate Changed");
            }

            FirebaseMessaging.getInstance().send(messageBuilder.build());
        } catch (FirebaseMessagingException e) {
            System.out.println(e);
        }
    }
}
