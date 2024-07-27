package com.vinayak.flight_status.flight;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlighs() {
        var now = new Date();
        Calendar calaender = Calendar.getInstance();
        calaender.setTime(now);
        calaender.set(Calendar.MONTH, 1);
        return flightRepository.findAllByDepartureDateGreaterThanEqualOrderByDepartureDateAsc(calaender.getTime());
    }

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Optional<Flight> getFlight(int id) {
        return flightRepository.findById(id);
    }

    public Flight updateFlight(Flight flight) {
        return flightRepository.save(flight);
    }

}
