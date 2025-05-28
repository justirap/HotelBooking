package com.example.hotelbooking;

import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.repository.RoomRepository;
import com.example.hotelbooking.service.RoomService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RoomServiceTest {

    private final RoomRepository repo = Mockito.mock(RoomRepository.class);
    private final RoomService service = new RoomService(repo);

    @Test
    void shouldReturnAllRooms() {
        Mockito.when(repo.findAll()).thenReturn(List.of(new Room(), new Room()));

        List<Room> result = service.getAllRooms();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnRoomById() {
        Room room = new Room();
        room.setId(1L);
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(room));

        Optional<Room> found = service.getRoomById(1L);

        assertThat(found).isPresent().contains(room);
    }

    @Test
    void shouldDeleteRoom() {
        service.deleteRoom(2L);
        Mockito.verify(repo).deleteById(2L);
    }
    @Test
    void shouldUpdateRoom() {
        Room existing = new Room();
        existing.setId(1L);
        existing.setType("STANDARD");
        existing.setAvailable(true);
        existing.setNumber("100");
        existing.setPricePerNight(200.0);

        Room updated = new Room();
        updated.setType("DELUXE");
        updated.setAvailable(false);
        updated.setNumber("101");
        updated.setPricePerNight(350.0);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repo.save(Mockito.any(Room.class))).thenAnswer(i -> i.getArgument(0));

        Room result = service.updateRoom(1L, updated);

        assertThat(result.getType()).isEqualTo("DELUXE");
        assertThat(result.isAvailable()).isFalse();
        assertThat(result.getNumber()).isEqualTo("101");
        assertThat(result.getPricePerNight()).isEqualTo(350.0);
    }
    @Test
    void shouldThrowWhenUpdatingNonexistentRoom() {
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateRoom(99L, new Room()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Room not found");
    }
}
