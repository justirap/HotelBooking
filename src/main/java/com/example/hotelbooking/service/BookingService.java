package com.example.hotelbooking.service;


import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public void deleteBookingById(Long id) {
        bookingRepository.deleteById(id);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setCheckInDate(updatedBooking.getCheckInDate());
            booking.setCheckOutDate(updatedBooking.getCheckOutDate());
            booking.setRoom(updatedBooking.getRoom());
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}