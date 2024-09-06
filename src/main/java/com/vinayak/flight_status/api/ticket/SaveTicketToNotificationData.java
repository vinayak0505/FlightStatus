package com.vinayak.flight_status.api.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveTicketToNotificationData {
    Integer userId;
    Integer flightId;
}
