package com.app.movieticket.repositories;

import com.app.movieticket.models.HallSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallSeatRepository extends JpaRepository<HallSeat, Long> {
}
