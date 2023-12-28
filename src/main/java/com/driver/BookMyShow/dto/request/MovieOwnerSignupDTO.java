package com.driver.BookMyShow.dto.request;

import com.driver.BookMyShow.enums.UserType;
import com.driver.BookMyShow.models.Movie;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MovieOwnerSignupDTO {
    String name;
    String email;
    long phoneNo;
    String password;
    UserType type; //MovieOwner, HallOwner, RegularUser
    List<Movie> movies;
    int companyAge;
}
