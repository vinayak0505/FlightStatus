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
import com.vinayak.flight_status.api.kafka.KafkaProducer;
import com.vinayak.flight_status.api.users.Users;
import com.vinayak.flight_status.api.users.UsersService;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    final private TicketService ticketService;
    final private UsersService usersService;
    final private FlightService flightService;
    final private KafkaProducer kafkaProducer;

    public TicketController(TicketService ticketService, UsersService usersService, FlightService flightService, KafkaProducer kafkaProducer) {
        this.ticketService = ticketService;
        this.usersService = usersService;
        this.flightService = flightService;
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("")
    public ResponseEntity<List<TicketResponseDto>> getAllTicket(Principal principal) {

        String name = principal.getName();
        Users user = usersService.getUser(name);

        List<Ticket> tickets = ticketService.getTicketsByUser(user.getId());
        return ResponseEntity.ok(tickets.stream().map(TicketResponseDto::new).toList());
    }

    @PostMapping("")
    public ResponseEntity<TicketResponseDto> buyTicket(@RequestBody BuyTicketRequestDto ticket, Principal principal) {
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
        // TODO testing
        kafkaProducer.sendSaveTicketToNotification(SaveTicketToNotificationData.builder().userId(user.getId()).flightId(flight.get().getId()).build());
        entity.setFlight(flight.get());
        return ResponseEntity.ok(new TicketResponseDto(entity));
    }

    @GetMapping("flight/{id}")
    public ResponseEntity<List<Integer>> getBookedSeatsByFlights(@PathVariable("id") int flightId) {
        List<Integer> tickets = ticketService.getBookedSeatsByFlightId(flightId);
        return ResponseEntity.ok(tickets);
    }
}
