package com.app.movieticket.repositories;

import com.app.movieticket.models.Booking;
import com.app.movieticket.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getBookingsByCustomer(Customer customer);
}
