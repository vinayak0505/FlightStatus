package com.vinayak.flight_status.api.ticket;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.vinayak.flight_status.api.flight.Flight;
import com.vinayak.flight_status.api.users.Users;

import jakarta.persistence.Column;
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
        this.users = Users.builder().id(userId).build();
        this.seatNumber = ticket.getSeatNumber();
    }

    @ManyToOne
    @Id
    private Flight flight;

    @Id
    @ManyToOne
    private Users users;

    @Id
    private Integer seatNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private Date createdAt;
}
