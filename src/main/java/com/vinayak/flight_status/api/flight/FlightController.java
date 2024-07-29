package com.vinayak.flight_status.api.flight;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinayak.flight_status.api.notification.NotificationService;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final NotificationService notificationService;

    public FlightController(FlightService flightService, NotificationService notificationService) {
        this.flightService = flightService;
        this.notificationService = notificationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Flight>> getAllFlights() {
        
        List<Flight> allFlighs = flightService.getAllFlighs();

        return ResponseEntity.ok().body(allFlighs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlight(@PathVariable int id) {
        
        Optional<Flight> flight = flightService.getFlight(id);
        if(flight == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(flight.get());
    }

    @PostMapping("")
    public ResponseEntity<Void> postFlight(@RequestBody Flight flight) {

        Flight dbFlight = flightService.addFlight(flight);

        if(dbFlight == null){
            return ResponseEntity.internalServerError().build();
        }

        URI locationOfNewFlight = URI.create("/flights/" + dbFlight.getId());
        return ResponseEntity
                .created(locationOfNewFlight)
                .build();
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlightStatus(@PathVariable int id, @RequestBody UpdateFlightStatusRequestDto flight) {
        
        Flight dbFlight = flightService.updateFlight(id, flight);

        if(dbFlight == null){
            return ResponseEntity.internalServerError().build();
        }

        notificationService.flightStatusUpdated(id, dbFlight.getFlightStatus());

        return ResponseEntity.ok().body(dbFlight);
    }

    @PutMapping("gate/{id}")
    public ResponseEntity<Flight> updateFlightGate(@PathVariable int id, @RequestBody UpdateFlightGateRequestDto gate) {
        Flight dbFlight = flightService.updateGate(id, gate.getGateNumber());

        if(dbFlight == null){
            return ResponseEntity.internalServerError().build();
        }

        notificationService.flightGateUpdated(id, dbFlight.getGateNumber());

        return ResponseEntity.ok().body(dbFlight);
    }

}