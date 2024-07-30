package com.vinayak.flight_status.api.flight;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // we can get only the flights that will be on current month and after
    // but because of lesst data commenting this code
    public List<Flight> getAllFlighs() {
        // var now = new Date();
        // Calendar calaender = Calendar.getInstance();
        // calaender.setTime(now);
        // calaender.set(Calendar.DAY_OF_MONTH, 1);
        // return flightRepository.findAllByDepartureDateGreaterThanEqualOrderByDepartureDateAsc(calaender.getTime());
        return flightRepository.findAllByOrderByCreatedAtDesc();
    }

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public Optional<Flight> getFlight(int id) {
        return flightRepository.findById(id);
    }

    // updating flight status and calling send notification thread
    public Flight updateFlight(Integer flightId, UpdateFlightStatusRequestDto updateFlight) {
        if (flightId == null || updateFlight == null || updateFlight.getFlightStatus() == null) {
            return null;
        }
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) {
            return null;
        }
        flight.setFlightStatus(updateFlight.getFlightStatus());
        Flight save = flightRepository.save(flight);
        return save;
    }

    // updating gate status and calling send notification thread
    public Flight updateGate(Integer flightId, String gateId) {
        if (flightId == null || gateId == null) {
            return null;
        }
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) {
            return null;
        }
        flight.setGateNumber(gateId);
        Flight save = flightRepository.save(flight);
        return save;
    }

}
