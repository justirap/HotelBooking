package com.example.hotelbooking;

import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.repository.UserRepository;
import com.example.hotelbooking.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class UserServiceTest {

    private final UserRepository repo = Mockito.mock(UserRepository.class);
    private final UserService service = new UserService(repo);

    @Test
    void shouldCreateUser() {
        User user = new User();
        user.setUsername("jan");

        Mockito.when(repo.save(user)).thenReturn(user);

        User result = service.createUser(user);

        assertThat(result.getUsername()).isEqualTo("jan");
    }

    @Test
    void shouldReturnUserById() {
        User user = new User();
        user.setId(2L);
        user.setUsername("ania");

        Mockito.when(repo.findById(2L)).thenReturn(Optional.of(user));

        Optional<User> found = service.getUserById(2L);

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("ania");
    }
    @Test
    void shouldUpdateUser() {
        User existing = new User();
        existing.setId(1L);
        existing.setUsername("old");
        existing.setPassword("pass");
        existing.setRole(User.Role.USER);

        User updated = new User();
        updated.setUsername("new");
        updated.setPassword("newpass");
        updated.setRole(User.Role.ADMIN);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repo.save(Mockito.any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = service.updateUser(1L, updated);

        assertThat(result.getUsername()).isEqualTo("new");
        assertThat(result.getPassword()).isEqualTo("newpass");
        assertThat(result.getRole()).isEqualTo(User.Role.ADMIN);
    }
    @Test
    void shouldThrowWhenUserNotFound() {
        Mockito.when(repo.findById(123L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateUser(123L, new User()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");
    }
}
