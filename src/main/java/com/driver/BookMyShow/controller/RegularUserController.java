package com.driver.BookMyShow.controller;

import com.driver.BookMyShow.dto.request.RegularUserSignupDTO;
import com.driver.BookMyShow.models.ApplicationUser;
import com.driver.BookMyShow.service.RegularUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Regular User API", description = "This controller contains all the Regular User related service endpoint details.")
@RestController
@RequestMapping("/user")
public class RegularUserController {
    @Autowired
    RegularUserService regularUserService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody RegularUserSignupDTO regularUserSignupDTO) {
        ApplicationUser user = regularUserService.signup(regularUserSignupDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
