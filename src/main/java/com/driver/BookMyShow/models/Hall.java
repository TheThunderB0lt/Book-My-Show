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
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String name;
    String address;

    @ManyToOne //Many halls hold 1 particular owner --> many PVRs in many locations that hold by 1 owner
    ApplicationUser owner;

    //We don't want to create another TB for storing HallId with List of screens so for that we use mappedBy to map into same table
    @OneToMany(mappedBy = "hall") // A particular hall contains many Screens --> Bi-Directional relation
    List<Screen> screens; // List of screens for a particular hall (1Hl : MScr)

    @OneToMany(mappedBy = "hall") // A particular hall may run many shows --> (1:M)
    List<Show> shows; // List of all shows should be display in the 1 hall POV (1Hl : MSw)

    @OneToMany(mappedBy = "hall")
    List<Ticket> tickets; // List of tickets for a particular movie (1Hl : MTk)
}
