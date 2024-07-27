package com.vinayak.flight_status.api.ticket;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @EmbeddedId
    private TicketCompositeKey id;

    @CreationTimestamp
    private Date createdAt;
}
