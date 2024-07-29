package com.vinayak.flight_status.api.messaging;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vinayak.flight_status.api.notification.Notification;
import com.vinayak.flight_status.api.ticket.Ticket;
import com.vinayak.flight_status.api.ticket.TicketService;

@Service
public class SubscriptionService {
    final private TicketService ticketService;
    final private FirebaseService firebaseService;

    public SubscriptionService(TicketService ticketService, FirebaseService firebaseService) {
        this.ticketService = ticketService;
        this.firebaseService = firebaseService;
    }

    public void unSubscribeAll(Notification notification) {
        // TODO Auto-generated method stub
        // get all tickets with unique flight id
        // for each flight getNotificationToken
        // throw new UnsupportedOperationException("Unimplemented method 'unSubscribeAll'");
    }

    public void subscribeUserTokenToTickets(Notification notification) {
        // get all tickets with unique flight id and user id
        List<Ticket> ticketsByUser = ticketService.getTicketsByUser(notification.getUserId());
        ticketsByUser.forEach(action -> {
            firebaseService.subscribe(Notification.getChannelName(action.getFlight().getId()), List.of(notification.getMessageToken()));
        });

        // subscribe to all flights for active user
        firebaseService.subscribe(Notification.getAllFlightChangeName(), List.of(notification.getMessageToken()));
    }

    public void subscribeUserTokensToTicket(Integer flightId, List<Notification> notifications) {
        List<String> tokens = notifications.stream().map(Notification::getMessageToken).toList();
        firebaseService.subscribe(Notification.getChannelName(flightId), tokens);
    }
}
