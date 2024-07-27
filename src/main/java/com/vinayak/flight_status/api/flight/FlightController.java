package com.vinayak.flight_status.api.flight;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("")
    public ResponseEntity<List<Flight>> getAllFlights() {
        
        List<Flight> allFlighs = flightService.getAllFlighs();

        return ResponseEntity.ok().body(allFlighs);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<List<Flight>> getAllFlightByUserId(@PathVariable int id) {
    //     Flight flight = flightService.getFlight(id).orElse(null);
    //     if(flight == null)
    //         return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok().body(flight);
    //     TODO: not implemented
    //     return ResponseEntity.badRequest().build();
    // }

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
    public ResponseEntity<Void> updateFlight(@PathVariable int id, @RequestBody Flight flight) {
        
        flight.setId(id);
        Flight dbFlight = flightService.updateFlight(flight);

        if(dbFlight == null){
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

}