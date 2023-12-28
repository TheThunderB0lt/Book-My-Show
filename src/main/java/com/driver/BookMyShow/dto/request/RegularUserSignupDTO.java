package com.driver.BookMyShow.dto.request;

import com.driver.BookMyShow.enums.UserType;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegularUserSignupDTO {
    String name;
    String email;
    long phoneNo;
    String password;
    UserType type; //MovieOwner, Admin, RegularUser
    int age;
}
