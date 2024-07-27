package com.vinayak.flight_status.api.ticket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyTicketRequestDto {

    private Integer flightId;
    private Integer seatNumber;
}
