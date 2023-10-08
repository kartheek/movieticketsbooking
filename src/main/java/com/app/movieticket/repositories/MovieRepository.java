package com.app.movieticket.repositories;

import com.app.movieticket.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByName(String movieName);
}
