package com.vinayak.flight_status.api.ticket;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class TicketService {

    final private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getTicketsByUser(Integer id) {
        List<Ticket> allByUserId = ticketRepository.findAllByUsersId(id);
        return allByUserId;
    }

    public Ticket createTicket(Ticket ticket) {
        Optional<Ticket> byId = ticketRepository.findById(new TicketCompositeKey(ticket));
        if(byId.isPresent()) {
            return null;
        }
        return ticketRepository.save(ticket);
    }

    public List<Integer> getBookedSeatsByFlightId(Integer flightId) {
        List<Ticket> allByFlight = ticketRepository
                .findAllByFlightId(flightId);

        List<Integer> allByFlightId = allByFlight
                .stream()
                .map(Ticket::getSeatNumber)
                .toList();
                
        return allByFlightId;
    }
}
