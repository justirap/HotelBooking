package com.example.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String type;
    private double pricePerNight;
    private boolean available;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}