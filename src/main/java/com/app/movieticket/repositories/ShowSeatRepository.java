package com.app.movieticket.repositories;

import com.app.movieticket.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
}
