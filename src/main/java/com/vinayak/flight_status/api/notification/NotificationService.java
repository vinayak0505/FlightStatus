package com.vinayak.flight_status.api.notification;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.email.EmailDetails;
import com.vinayak.flight_status.api.email.EmailService;
import com.vinayak.flight_status.api.flight.FlightStatus;
import com.vinayak.flight_status.api.messaging.FirebaseService;
import com.vinayak.flight_status.api.messaging.SubscriptionService;
import com.vinayak.flight_status.api.ticket.TicketRepository;
import com.vinayak.flight_status.api.ticket.TicketUserId;

@Service
public class NotificationService {

    final private NotificationRepository notificationRepository;
    final private SubscriptionService subscriptionService;
    final private FirebaseService firebaseService;
    final private EmailService emailService;
    final private TicketRepository ticketRepository;

    public NotificationService(NotificationRepository notificationRepository, SubscriptionService subscriptionService, FirebaseService firebaseService, EmailService emailService, TicketRepository ticketRepository) {
        this.notificationRepository = notificationRepository;
        this.subscriptionService = subscriptionService;
        this.firebaseService = firebaseService;
        this.emailService = emailService;
        this.ticketRepository = ticketRepository;
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
        firebaseService.sendFlightStatus(NotificationType.SFLIGHT, Notification.getChannelName(flightId), flightStatus, flightId);
        firebaseService.sendFlightStatus(NotificationType.ALLFLIGHT, Notification.getAllFlightChangeName(), flightStatus, flightId);
        List<TicketUserId> allByFlightId = ticketRepository.findAllDistinctUsersIdByFlightId(flightId);

        for (TicketUserId ticket : allByFlightId) {
            emailService.sendSimpleMail(
                    EmailDetails.builder()
                            .recipient(ticket.getUsers().getEmail())
                            .subject("Flight Status Updated")
                            .msgBody("Flight Status update for flight " + flightId + " with status " + flightStatus)
                            .build()
            );
        }
    }

    public void flightGateUpdated(Integer flightId, String gateId) {
        firebaseService.sendFlightGate(NotificationType.SGATE, Notification.getChannelName(flightId), gateId, flightId);
        firebaseService.sendFlightGate(NotificationType.ALLGATE, Notification.getAllFlightChangeName(), gateId, flightId);
        List<TicketUserId> allByFlightId = ticketRepository.findAllDistinctUsersIdByFlightId(flightId);
        for (TicketUserId ticket : allByFlightId) {
            emailService.sendSimpleMail(
                    EmailDetails.builder()
                            .recipient(ticket.getUsers().getEmail())
                            .subject("Your Gate Number changed")
                            .msgBody("Your Gate Number changed to " + gateId + " for flight " + flightId)
                            .build()
            );
        }
    }
}
