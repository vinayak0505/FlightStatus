package com.vinayak.flight_status.api.ticket;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.flight_status.api.flight.Flight;
import com.vinayak.flight_status.api.flight.FlightService;
import com.vinayak.flight_status.api.notification.NotificationService;
import com.vinayak.flight_status.api.users.Users;
import com.vinayak.flight_status.api.users.UsersService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    final private TicketService ticketService;
    final private UsersService usersService;
    final private FlightService flightService;
    final private NotificationService notificationService;

    public TicketController(TicketService ticketService, UsersService usersService, FlightService flightService, NotificationService notificationService) {
        this.ticketService = ticketService;
        this.usersService = usersService;
        this.flightService = flightService;
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAllTicket(Principal principal) {

        String name = principal.getName();
        Users user = usersService.getUser(name);

        List<Ticket> tickets = ticketService.getTicketsByUser(user.getId());
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("")
    public ResponseEntity<Ticket> buyTicket(@RequestBody BuyTicketRequestDto ticket, Principal principal) {
        String name = principal.getName();
        Users user = usersService.getUser(name);

        Ticket entity = ticketService.createTicket(new Ticket(ticket, user.getId()));

        if(entity == null){
            return ResponseEntity.badRequest().build();
        }
        
        Optional<Flight> flight = flightService.getFlight(entity.getFlight().getId());

        if (!flight.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        notificationService.saveTicketToNotification(user.getId(), flight.get().getId());
        entity.setFlight(flight.get());
        return ResponseEntity.ok(entity);
    }

    @GetMapping("flight/{id}")
    public ResponseEntity<List<Integer>> getBookedSeatsByFlights(@PathVariable("id") int flightId) {
        List<Integer> tickets = ticketService.getBookedSeatsByFlightId(flightId);
        return ResponseEntity.ok(tickets);
    }
}
