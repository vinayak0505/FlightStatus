package com.vinayak.flight_status.flight;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
public class Flight {

    @Id
    @GeneratedValue
    private Integer id;

    private String flightNumber;

    private String source;

    private String destination;

    private Date departureDate;

    private Date arrivalDate;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FlightStatus flightStatus = FlightStatus.ON_TIME;

    private String gateNumber;

    private Integer seatCount;
}
