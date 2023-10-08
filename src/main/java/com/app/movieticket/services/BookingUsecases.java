package com.app.movieticket.services;

import com.app.movieticket.dtos.CreateBookingDTO;
import com.app.movieticket.dtos.ShowSeatDTO;
import com.app.movieticket.exceptions.booking.InvalidBookingStateException;
import com.app.movieticket.exceptions.booking.SeatsNotAvailableException;
import com.app.movieticket.models.Booking;
import com.app.movieticket.models.BookingStatus;
import com.app.movieticket.models.Customer;
import com.app.movieticket.models.ShowSeat;
import com.app.movieticket.repositories.BookingRepository;
import com.app.movieticket.repositories.ShowSeatRepository;
import com.app.movieticket.services.RefundService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingUsecases {
    private final ShowSeatRepository showSeatRepository;
    private final RefundService refundService;
    private final BookingRepository bookingRepository;

    public BookingUsecases(ShowSeatRepository showSeatRepository, RefundService refundService, BookingRepository bookingRepository) {
        this.showSeatRepository = showSeatRepository;
        this.refundService = refundService;
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Customer customer, CreateBookingDTO details) {
        if (!details.getShow().isShowPending()) {
            throw new SeatsNotAvailableException("The show is no longer accepting bookings");
        }
        Booking booking;
        // todo: Acquire some lock while booking the tickets(maybe redis locks)
        {
            boolean preOccupied = details
                    .getShowSeats()
                    .stream()
                    .anyMatch(ShowSeat::isOccupied);
            if (preOccupied) {
                throw new SeatsNotAvailableException("Some of the seats are no longer available");
            }
            for (ShowSeat showSeat : details.getShowSeats()) {
                showSeat.setOccupied(true);
                showSeatRepository.save(showSeat);
            }
            booking = new Booking(customer, details.getShow());
            booking.setSeatsBooked(details.getShowSeats());
            bookingRepository.save(booking);
        }
        // todo: Release the lock
        return booking;
    }

    public Booking cancelBooking(Booking booking) {
        if (!booking.getShow().isShowPending()) {
            throw new InvalidBookingStateException("The show has already started/completed. This booking cannot be cancelled now");
        }
        refundService.refundBooking(booking);
        booking.setStatus(BookingStatus.CANCELLED);
        for (ShowSeat seat : booking.getSeatsBooked()) {
            seat.setOccupied(false);
            showSeatRepository.save(seat);
        }
        bookingRepository.save(booking);
        return booking;
    }

    public List<Booking> listCustomerBookings(Customer customer) {
        return bookingRepository.getBookingsByCustomer(customer);
    }
}
