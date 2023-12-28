package com.driver.BookMyShow.dto.request;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddShowDTO {
    int hour;
    int minutes;
    int ticketPrice;
    UUID movieId;
    UUID hallId;
}
