package com.driver.BookMyShow.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @JsonIgnore
    @ManyToOne //Many shows will run in 1 particular hall (M:1)
    Hall hall; //In which particular hall the show is running

    @JsonIgnore
    @ManyToOne
    Movie movie;

    @JsonIgnore
    @ManyToOne // A particular Show runs in multiple screens (M:1)
    Screen screen;

    int availableTickets;
    Date startTime;
    Date endTime;
    int ticketPrice;

    @OneToMany(mappedBy = "show") // A particular show has many tickets
    List<Ticket> tickets; //List of all tickets for 1 particular show
}
