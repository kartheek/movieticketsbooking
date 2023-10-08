package com.app.movieticket.repositories;

import com.app.movieticket.models.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
