package com.driver.BookMyShow.service;

import com.driver.BookMyShow.models.Hall;
import com.driver.BookMyShow.models.Show;
import com.driver.BookMyShow.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ShowService {

    @Autowired
    ShowRepository showRepository;

    public void createShow(Show show) {
        showRepository.save(show);
    }

    public List<Show> getShowByMovieId(UUID movieId) {
        return showRepository.getShowByMovieId(movieId);
    }

    public List<Show> getShowByHallId(UUID hallId) {
        return showRepository.getShowByHallId(hallId);
    }

    public List<Show> getShowByHallIdAndMovieId(UUID hallId, UUID movieId) {
        return showRepository.getShowByHallIdAndMovieId(hallId, movieId);
    }
}
