package com.vinayak.flight_status.api.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveMessagingTokenData {
    private int userId;
    private String firebaseToken;
}
