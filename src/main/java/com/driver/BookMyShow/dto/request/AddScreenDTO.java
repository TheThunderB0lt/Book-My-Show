package com.driver.BookMyShow.dto.request;

import com.driver.BookMyShow.models.Screen;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddScreenDTO {
    List<Screen> screens;
    UUID hallId;
}
