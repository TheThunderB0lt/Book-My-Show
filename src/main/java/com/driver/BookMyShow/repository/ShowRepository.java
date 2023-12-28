package com.driver.BookMyShow.repository;

import com.driver.BookMyShow.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShowRepository extends JpaRepository<Show, UUID> {

    @Query(value = "select * from show where movie_id=:id", nativeQuery = true)
    public List<Show> getShowByMovieId(UUID id);
}
