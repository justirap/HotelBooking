package com.example.hotelbooking;

import com.example.hotelbooking.controller.RoomController;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllRooms() throws Exception {
        Mockito.when(roomService.getAllRooms()).thenReturn(List.of(new Room(), new Room()));

        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnRoomById() throws Exception {
        Room room = new Room();
        room.setId(1L);
        room.setNumber("101");

        Mockito.when(roomService.getRoomById(1L)).thenReturn(Optional.of(room));

        mockMvc.perform(get("/api/rooms/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("101"));
    }

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room();
        room.setNumber("102");
        room.setType("STANDARD");
        room.setAvailable(true);
        room.setPricePerNight(250.0);

        Mockito.when(roomService.createRoom(Mockito.any(Room.class))).thenReturn(room);

        mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value("102"));
    }

    @Test
    void shouldReturn404IfRoomNotFound() throws Exception {
        Mockito.when(roomService.getRoomById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/rooms/99"))
                .andExpect(status().isNotFound());
    }
}