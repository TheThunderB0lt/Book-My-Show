package com.driver.BookMyShow.dto.request;

import com.driver.BookMyShow.enums.UserType;
import com.driver.BookMyShow.models.Hall;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HallOwnerSignupDTO {
    String name;
    String email;
    long phoneNo;
    String password;
    UserType type; //MovieOwner, HallOwner, RegularUser
    List<Hall> halls;
    int companyAge;
}
