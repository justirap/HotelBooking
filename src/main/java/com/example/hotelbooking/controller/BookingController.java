package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking", description = "Booking operations")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBookingById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{bookingId}/users")
    public ResponseEntity<?> assignUserToBooking(@PathVariable Long bookingId, @RequestBody Long userId) {
        Optional<Booking> optionalBooking = bookingService.getBookingById(bookingId);
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalBooking.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Booking booking = optionalBooking.get();
        booking.setUser(optionalUser.get());
        bookingService.updateBooking(bookingId, booking);
        return ResponseEntity.ok().build();
    }

}
