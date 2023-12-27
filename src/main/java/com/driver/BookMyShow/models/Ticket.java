package com.driver.BookMyShow.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @ManyToOne //A particular user can buy many tickets (M:1)
    ApplicationUser user; // (TB --> Hall)

    @ManyToOne // A particular movie have many tickets, & A particular ticket is for 1 movie
    Movie movie; // Details of movie (TB --> Hall)

    @ManyToOne //A particular contains many tickets for 1 movie
    Hall hall; //(TB --> in Hall)

    @ManyToOne // A particular hall has many shows
    Show show; // (Tb --> hall)
}