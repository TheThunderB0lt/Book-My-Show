package com.driver.BookMyShow.controller;

import com.driver.BookMyShow.dto.request.MovieOwnerSignupDTO;
import com.driver.BookMyShow.models.ApplicationUser;
import com.driver.BookMyShow.service.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Movie API", description = "This controller contains all the Movie related service endpoint details.")
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping("/signup")
    public ResponseEntity signUp(@RequestBody MovieOwnerSignupDTO movieOwnerSignupDTO) {
        ApplicationUser user = movieService.signup(movieOwnerSignupDTO);
        return new ResponseEntity(user, HttpStatus.CREATED);
    }
}
