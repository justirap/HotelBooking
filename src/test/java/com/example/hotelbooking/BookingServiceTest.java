package com.example.hotelbooking;

import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.repository.BookingRepository;
import com.example.hotelbooking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BookingServiceTest {

    private final BookingRepository repo = Mockito.mock(BookingRepository.class);
    private final BookingService service = new BookingService(repo);

    @Test
    void shouldCreateBooking() {
        Booking booking = new Booking();
        booking.setCheckInDate(LocalDate.now());

        Mockito.when(repo.save(booking)).thenReturn(booking);

        Booking result = service.createBooking(booking);

        assertThat(result.getCheckInDate()).isEqualTo(LocalDate.now());
    }

    @Test
    void shouldFindBookingById() {
        Booking booking = new Booking();
        booking.setId(5L);

        Mockito.when(repo.findById(5L)).thenReturn(Optional.of(booking));

        Optional<Booking> found = service.getBookingById(5L);

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(5L);
    }

    @Test
    void shouldUpdateBooking() {
        Booking existing = new Booking();
        existing.setId(1L);
        existing.setCheckInDate(LocalDate.of(2024, 1, 1));
        existing.setCheckOutDate(LocalDate.of(2024, 1, 5));

        Booking updated = new Booking();
        updated.setCheckInDate(LocalDate.of(2024, 2, 1));
        updated.setCheckOutDate(LocalDate.of(2024, 2, 3));

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repo.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        Booking result = service.updateBooking(1L, updated);

        assertThat(result.getCheckInDate()).isEqualTo(LocalDate.of(2024, 2, 1));
    }

    @Test
    void shouldThrowIfBookingMissing() {
        Mockito.when(repo.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateBooking(42L, new Booking()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Booking not found");
    }

    @Test
    void shouldDeleteBooking() {
        service.deleteBookingById(9L);
        Mockito.verify(repo).deleteById(9L);
    }
}