package com.vinayak.flight_status;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.vinayak.flight_status.api.flight.Flight;
import com.vinayak.flight_status.api.flight.FlightRepository;
import com.vinayak.flight_status.api.ticket.Ticket;
import com.vinayak.flight_status.api.ticket.TicketRepository;
import com.vinayak.flight_status.api.users.Users;
import com.vinayak.flight_status.api.users.UsersRepository;
import com.vinayak.flight_status.api.users.UsersRole;

@SpringBootApplication
public class FlightStatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightStatusApplication.class, args);
    }

    // @Bean
    @Autowired
    public CommandLineRunner dummy(
            UsersRepository usersRepository,
            FlightRepository flightRepository,
            TicketRepository ticketRepository
    ) {
        return args -> {
            for (int i = 0; i < 10; i++) {
                Faker faker = new Faker();
                Users users = Users.builder()
                        .fullName(faker.name().fullName())
                        .email(faker.internet().emailAddress())
                        .password(faker.internet().password())
                        .build();
                usersRepository.save(users);

                var flight = Flight.builder()
                        .flightNumber(faker.number().numberBetween(1000, 9999) + "_flight")
                        .source(faker.address().city())
                        .destination(faker.address().city())
                        .departureDate(faker.date().past(10, TimeUnit.DAYS))
                        .seatCount(faker.number().numberBetween(100, 200))
                        .arrivalDate(faker.date().future(10, TimeUnit.DAYS))
                        .gateNumber(faker.number().numberBetween(0, 10) + "_gate")
                        .price(faker.number().numberBetween(1000, 10000))
                        .build();

                flightRepository.save(flight);

                var ticket = Ticket.builder().flight(Flight.builder().id(flight.getId()).build())
                        .userId(users.getId())
                        .seatNumber(faker.number().numberBetween(0, flight.getSeatCount()))
                        .build();

                ticketRepository.save(ticket);
            }
        };
    }

    // @Bean
    @Autowired
    public CommandLineRunner mydata(
            UsersRepository usersRepository
    ) {

        return args -> {

            Users users = Users.builder()
                    .fullName("Vinayak Agarwal")
                    .email("vinayakaggarwal05@gmail.com")
                    .password("qwerty12345")
                    .role(UsersRole.ADMIN)
                    .build();

            usersRepository.save(users);
        };
    }

}
