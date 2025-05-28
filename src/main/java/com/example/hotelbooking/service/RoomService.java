package com.example.hotelbooking.service;

import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        return roomRepository.findById(id).map(room -> {
            room.setType(updatedRoom.getType());
            room.setPricePerNight(updatedRoom.getPricePerNight());
            room.setAvailable(updatedRoom.isAvailable());
            room.setNumber(updatedRoom.getNumber());
            return roomRepository.save(room);
        }).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
