package com.example.hotelbooking;

import com.example.hotelbooking.controller.BookingController;
import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllBookings() throws Exception {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        booking1.setCheckInDate(LocalDate.of(2025, 6, 1));
        booking1.setCheckOutDate(LocalDate.of(2025, 6, 10));

        Booking booking2 = new Booking();
        booking2.setId(2L);
        booking2.setCheckInDate(LocalDate.of(2025, 7, 1));
        booking2.setCheckOutDate(LocalDate.of(2025, 7, 10));

        Mockito.when(bookingService.getAllBookings()).thenReturn(List.of(booking1, booking2));

        mockMvc.perform(get("/api/bookings/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnBookingById() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCheckInDate(LocalDate.of(2025, 5, 1));
        booking.setCheckOutDate(LocalDate.of(2025, 5, 5));

        Mockito.when(bookingService.getBookingById(1L)).thenReturn(Optional.of(booking));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldReturn404IfBookingNotFound() throws Exception {
        Mockito.when(bookingService.getBookingById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bookings/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateBooking() throws Exception {
        Booking booking = new Booking();
        booking.setCheckInDate(LocalDate.of(2025, 6, 1));
        booking.setCheckOutDate(LocalDate.of(2025, 6, 5));
        booking.setRoom(new Room()); // mock room
        booking.setUser(new User()); // mock user

        Mockito.when(bookingService.createBooking(Mockito.any(Booking.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkInDate").value("2025-06-01"))
                .andExpect(jsonPath("$.checkOutDate").value("2025-06-05"));
    }

    @Test
    void shouldUpdateBooking() throws Exception {
        Booking updated = new Booking();
        updated.setId(1L);
        updated.setCheckInDate(LocalDate.of(2025, 8, 1));
        updated.setCheckOutDate(LocalDate.of(2025, 8, 10));

        Mockito.when(bookingService.updateBooking(Mockito.eq(1L), Mockito.any(Booking.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.checkInDate").value("2025-08-01"))
                .andExpect(jsonPath("$.checkOutDate").value("2025-08-10"));
    }

    @Test
    void shouldDeleteBooking() throws Exception {
        Mockito.doNothing().when(bookingService).deleteBookingById(1L);

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAssignUserToBooking() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setCheckInDate(LocalDate.of(2025, 9, 1));
        booking.setCheckOutDate(LocalDate.of(2025, 9, 10));

        User user = new User();
        user.setId(2L);
        user.setUsername("user");

        Mockito.when(bookingService.getBookingById(1L)).thenReturn(Optional.of(booking));
        Mockito.when(userService.getUserById(2L)).thenReturn(Optional.of(user));
        Mockito.when(bookingService.updateBooking(Mockito.eq(1L), Mockito.any(Booking.class))).thenReturn(booking);

        mockMvc.perform(put("/api/bookings/1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("2"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenAssigningUserToNonexistentBooking() throws Exception {
        Mockito.when(bookingService.getBookingById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/bookings/1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn404WhenAssigningNonexistentUserToBooking() throws Exception {
        Booking booking = new Booking();
        booking.setId(1L);

        Mockito.when(bookingService.getBookingById(1L)).thenReturn(Optional.of(booking));
        Mockito.when(userService.getUserById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/bookings/1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("2"))
                .andExpect(status().isNotFound());
    }
}
