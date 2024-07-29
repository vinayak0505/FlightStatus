package com.vinayak.flight_status.api.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFlightGateRequestDto {

    private String gateNumber;
}
