package com.vinayak.flight_status.api.ticket;

import java.util.Date;

import com.vinayak.flight_status.api.flight.Flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketResponseDto {
    private Flight flight;
    private Integer seatNumber;
    private Integer userId;
    private Date createdAt;

    public TicketResponseDto(Ticket ticket) {
        this.flight = ticket.getFlight();
        this.seatNumber = ticket.getSeatNumber();
        this.userId = ticket.getUsers().getId();
        this.createdAt = ticket.getCreatedAt();
    }
}
