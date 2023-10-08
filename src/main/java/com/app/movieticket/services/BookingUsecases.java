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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookingUsecases {
    private final ShowSeatRepository showSeatRepository;
    private final RefundService refundService;
    private final BookingRepository bookingRepository;
    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Boolean> lockScript;
    private final RedisScript<Boolean> unlockScript;

    public BookingUsecases(
            ShowSeatRepository showSeatRepository,
            RefundService refundService,
            BookingRepository bookingRepository,
            StringRedisTemplate redisTemplate
    ) {
        this.showSeatRepository = showSeatRepository;
        this.refundService = refundService;
        this.bookingRepository = bookingRepository;
        this.redisTemplate = redisTemplate;
        this.lockScript = new DefaultRedisScript<>(
                "return redis.call('set', KEYS[1], ARGV[1], 'NX', 'EX', ARGV[2])",
                Boolean.class
        );
        this.unlockScript = new DefaultRedisScript<>(
                "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end",
                Boolean.class
        );
    }

    public Booking createBooking(Customer customer, CreateBookingDTO details) {
        if (!details.getShow().isShowPending()) {
            throw new SeatsNotAvailableException("The show is no longer accepting bookings");
        }

        String lockKey = "lock:show:" + details.getShow().getId(); // Unique lock key for the show

        // Acquire lock
        boolean locked = redisTemplate.execute(lockScript, Collections.singletonList(lockKey), customer.getId(), "300"); // Lock for 5 minutes (adjust as needed)

        if (!locked) {
            throw new SeatsNotAvailableException("Failed to acquire a lock for the show");
        }

        Booking booking;
        try {
            // Check seat availability
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

            // Payment processing logic here

            booking = new Booking(customer, details.getShow());
            booking.setSeatsBooked(details.getShowSeats());
            bookingRepository.save(booking);
        } finally {
            // Release the lock
            redisTemplate.execute(unlockScript, Collections.singletonList(lockKey), customer.getId());
        }

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
