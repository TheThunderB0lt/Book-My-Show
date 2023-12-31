package com.driver.BookMyShow.controller;

import com.driver.BookMyShow.models.Show;
import com.driver.BookMyShow.service.ShowService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Show API", description = "This controller contains all the Show related service endpoint details.")
@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    ShowService showService;

    @GetMapping("/search")
    public ResponseEntity searchShowByMovieId(@RequestParam(required = false) UUID movieId, @RequestParam(required = false) UUID hallId) {
        if(movieId != null && hallId != null) {
            List<Show> shows = showService.getShowByHallIdAndMovieId(hallId, movieId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else if(movieId == null && hallId != null) {
            List<Show> shows = showService.getShowByHallId(hallId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else if (movieId != null && hallId == null) {
            List<Show> shows = showService.getShowByMovieId(movieId);
            return new ResponseEntity(shows, HttpStatus.OK);
        }
        else {
            return new ResponseEntity("Please enter at least 1 param", HttpStatus.OK);
        }
    }

}
