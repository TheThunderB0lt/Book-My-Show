package com.driver.BookMyShow.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String Name;
    String directorName;
    String actorName;
    String actressName;
    int imdbRating;
    double duration; // In hours:mins

    @OneToMany(mappedBy = "movie")
    List<Ticket> tickets; //A particular can have many tickets

    @ManyToOne
    ApplicationUser owner;
}
