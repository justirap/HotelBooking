package com.example.hotelbooking.controller;

import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {


    private final BookingService bookingService;
    private final UserService userService;

    public LoginController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());

        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            List<Booking> allBookings = bookingService.getAllBookings();
            model.addAttribute("bookings", allBookings);
            System.out.println(authentication.getAuthorities());
            return "dashboard-admin";
        }


        User user = userService.findByUsername(authentication.getName()).orElse(null);
        if (user != null) {
            List<Booking> userBookings = bookingService.getBookingsByUser(user);
            model.addAttribute("bookings", userBookings);
        }

        return "dashboard-user";
    }
}