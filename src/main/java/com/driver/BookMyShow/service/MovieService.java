package com.driver.BookMyShow.service;

import com.driver.BookMyShow.dto.request.MovieOwnerSignupDTO;
import com.driver.BookMyShow.models.ApplicationUser;
import com.driver.BookMyShow.models.Movie;
import com.driver.BookMyShow.repository.ApplicationUserRepository;
import com.driver.BookMyShow.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    MovieRepository movieRepository;

    public ApplicationUser signup(MovieOwnerSignupDTO movieOwnerSignupDTO) {
        ApplicationUser movieOwner = new ApplicationUser();
        movieOwner.setName(movieOwnerSignupDTO.getName());
        movieOwner.setEmail(movieOwnerSignupDTO.getEmail());
        movieOwner.setPassword(movieOwnerSignupDTO.getPassword());
        movieOwner.setPhoneNo(movieOwnerSignupDTO.getPhoneNo());
        movieOwner.setType(movieOwnerSignupDTO.getType().toString());
        movieOwner.setAge(movieOwnerSignupDTO.getCompanyAge());
        applicationUserRepository.save(movieOwner); //saving ownerID

        List<Movie> movies = movieOwnerSignupDTO.getMovies();
        for (Movie movie : movies) {
            movie.setOwner(movieOwner);
            movieRepository.save(movie);
        }
        return movieOwner;
    }

    public Movie getMovieById(UUID id) {
        return movieRepository.findById(id).orElse(null);
    }
}
