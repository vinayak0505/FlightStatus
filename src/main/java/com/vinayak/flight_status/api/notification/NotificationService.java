package com.vinayak.flight_status.api.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.flight.FlightStatus;
import com.vinayak.flight_status.api.messaging.FirebaseService;
import com.vinayak.flight_status.api.messaging.SubscriptionService;

@Service
public class NotificationService {

    final private NotificationRepository notificationRepository;
    final private SubscriptionService subscriptionService;
    final private FirebaseService firebaseService;

    public NotificationService(NotificationRepository notificationRepository, SubscriptionService subscriptionService, FirebaseService firebaseService) {
        this.notificationRepository = notificationRepository;
        this.subscriptionService = subscriptionService;
        this.firebaseService = firebaseService;
    }

    public void saveMessagingToken(Integer userId, String firebase_token) {
        Optional<Notification> byId = notificationRepository.findById(firebase_token);

        if (byId.isPresent()) {
            if (byId.get().getUserId().equals(userId)) {
                return;
            }
            // this means there is already a user from this device
            // as a one firebase device can have only one user we will remove previous subscrption
            subscriptionService.unSubscribeAll(byId.get());
        }

        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessageToken(firebase_token);
        notificationRepository.save(notification);

        subscriptionService.subscribeUserTokenToTickets(notification);
    }

    public void saveTicketToNotification(Integer userId, Integer flightId) {
        // user have already saved messaging token while loggin in
        // so no need here
        List<Notification> notifications = notificationRepository.findAllByUserId(userId);
        subscriptionService.subscribeUserTokensToTicket(flightId, notifications);
    }

    public void flightStatusUpdated(Integer flightId, FlightStatus flightStatus) {
        firebaseService.sendFlightStatus(NotificationType.SFLIGHT,Notification.getChannelName(flightId), flightStatus, flightId);
        firebaseService.sendFlightStatus(NotificationType.ALLFLIGHT,Notification.getAllFlightChangeName(), flightStatus, flightId);
    }
}
