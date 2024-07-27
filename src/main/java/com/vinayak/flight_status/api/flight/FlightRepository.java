package com.vinayak.flight_status.api.flight;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    // sort by departure date and should not be less then current date
    public List<Flight> findAllByDepartureDateGreaterThanEqualOrderByDepartureDateAsc(Date departureDate);
}
