package com.vinayak.flight_status.api.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {

    @Id
    private String messageToken;

    private Integer userId;

    public static String getChannelName(int flightId){
        return "flight_id_" + flightId;
    }

    public static String getAllFlightChangeName(){
        return "all_flights";
    }
}
