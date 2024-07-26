package com.vinayak.flight_status.ticket;

import java.io.Serializable;

import com.vinayak.flight_status.flight.Flight;

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

    @ManyToOne
    private Flight flight;

    private Integer userId;

    private Integer seatNumber;
}
