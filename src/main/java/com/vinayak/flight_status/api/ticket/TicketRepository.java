package com.vinayak.flight_status.api.ticket;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, TicketCompositeKey> {

    List<Ticket> findAllByUsersId(Integer id);

    List<Ticket> findAllByFlightId(Integer flightId);
    
}
