package com.vinayak.flight_status.api.ticket;

import java.io.Serializable;

import com.vinayak.flight_status.api.flight.Flight;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class TicketCompositeKey implements Serializable {

    public TicketCompositeKey(Ticket ticket) {
        this.flight = ticket.getFlight();
        this.userId = ticket.getUserId();
        this.seatNumber = ticket.getSeatNumber();
    }

    @ManyToOne
    private Flight flight;

    private Integer userId;

    private Integer seatNumber;
}
