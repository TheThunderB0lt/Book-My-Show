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
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String name;
    int screenCapacity;

    @ManyToOne //Many Screen in 1 Hall -->Bi-Directional Relation
    Hall hall; //(TB --> in Hall)

    boolean status; // The available screens are Booked/Not Booked (T/F) on that particular screen we put a show
    String Type; // 2D,3D
}
