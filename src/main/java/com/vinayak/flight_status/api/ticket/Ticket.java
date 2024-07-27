package com.vinayak.flight_status.api.ticket;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.vinayak.flight_status.api.flight.Flight;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TicketCompositeKey.class)
public class Ticket {

    public Ticket(BuyTicketRequestDto ticket, Integer userId) {
        this.flight = Flight.builder().id(ticket.getFlightId()).build();
        this.userId = userId;
        this.seatNumber = ticket.getSeatNumber();
    }

    @ManyToOne
    @Id
    private Flight flight;

    @Id
    private Integer userId;

    @Id
    private Integer seatNumber;

    @CreationTimestamp
    private Date createdAt;
}
