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
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;
    String Name;

    @Column(unique = true)
    String email;
    @Column(unique = true)
    long phoneNo;

    String password;
    String type; //MovieOwner, Admin, RegularUser
    int age;

    @OneToMany(mappedBy = "user") //its basically going to create another TB (usrId_tktId) so for that we are mapping user TB to maintaining all the details
    List<Ticket> tickets;
}
