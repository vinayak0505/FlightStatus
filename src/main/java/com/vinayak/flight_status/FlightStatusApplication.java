package com.vinayak.flight_status;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.javafaker.Faker;
import com.vinayak.flight_status.api.email.EmailDetails;
import com.vinayak.flight_status.api.email.EmailService;
import com.vinayak.flight_status.api.flight.Flight;
import com.vinayak.flight_status.api.flight.FlightRepository;
import com.vinayak.flight_status.api.ticket.Ticket;
import com.vinayak.flight_status.api.ticket.TicketRepository;
import com.vinayak.flight_status.api.users.Users;
import com.vinayak.flight_status.api.users.UsersRepository;

@SpringBootApplication
public class FlightStatusApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightStatusApplication.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner dummyData(
            UsersRepository usersRepository,
            FlightRepository flightRepository,
            TicketRepository ticketRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Instant from = Instant.now().minus(Duration.ofDays(10));
            Instant to = Instant.now().plus(Duration.ofDays(40));
            for (int i = 0; i < 10; i++) {
                Faker faker = new Faker();
                Users users = Users.builder()
                        .fullName(faker.name().fullName())
                        .email(faker.internet().emailAddress())
                        .password(passwordEncoder.encode("qwerty12345"))
                        .build();

                usersRepository.save(users);
                Date departureDate = faker.date().between(Date.from(from), Date.from(to));

                var flight = Flight.builder()
                        .flightNumber(faker.number().numberBetween(1000, 9999) + "_flight")
                        .source(faker.address().city())
                        .destination(faker.address().city())
                        .departureDate(departureDate)
                        .arrivalDate(faker.date().future(48, TimeUnit.HOURS, departureDate))
                        .seatCount(faker.number().numberBetween(100, 200))
                        .gateNumber(faker.number().numberBetween(0, 10) + "A gate")
                        .price(faker.number().numberBetween(1000, 10000))
                        .build();

                flightRepository.save(flight);

                var ticket = Ticket.builder()
                        .flight(Flight.builder().id(flight.getId()).build())
                        .users(users)
                        .seatNumber(faker.number().numberBetween(0, flight.getSeatCount()))
                        .build();

                ticketRepository.save(ticket);
            }
        };
    }

    // @Bean
    @Autowired
    public CommandLineRunner sendMessageTemp(EmailService emailService) {
        return args -> emailService.sendSimpleMail(
                EmailDetails
                        .builder()
                        .recipient("vinayakaggarwal@gmail.com")
                        .subject("Flight Status Changed")
                        .msgBody("Flight on time. Don't forget to leave on time.")
                        .build()
        );
    }

}
