package com.app.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.test.entity.Users;
import com.app.test.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserRepositoryService userRepositoryService;

    private Users users;

    @BeforeEach
    void setup() {
        users = new Users();
        users.setId(1);
        users.setName("Cristian");
        users.setLastname("Moreno");
    }

    @Test
    void save() {
        // GIVEN
        given(userRepository.save(users)).willReturn(users);

        // WHEN
        Users result = userRepositoryService.save(users);

        // THEN
        assertNotNull(result);
        assertEquals(users, result);
    };

    @Test
    void update() {
        // GIVEN
        given(userRepository.save(users)).willReturn(users);

        // WHEN
        users.setName("Carlos");
        users.setLastname("Moreno");
        Users user = userRepositoryService.update(users);

        assertNotNull(user);
        assertEquals(users, user);
    };

    @Test
    void findById() {
        // GIVEN
        given(userRepository.findById(users.getId())).willReturn(Optional.of(users));

        // WHEN
        Optional<Users> result = userRepositoryService.findById(users.getId());
        Users user = result.get();

        // THEN
        assertNotNull(user);
        assertEquals(users, user);
    };

    @Test
    void findByName() {
        // GIVEN
        given(userRepository.findByName(users.getName())).willReturn(Optional.of(users));

        // WHEN
        Optional<Users> result = userRepositoryService.findByName(users.getName());
        Users user = result.get();

        // THEN
        assertNotNull(user);
        assertEquals(users, user);
    };

    @Test
    void deleteById() {
        // GIVEN
        // NOTHING

        // WHEN
        userRepositoryService.deleteById(users.getId());

        // THEN
        verify(userRepository).deleteById(users.getId());
    };

    @Test
    void findByUsername() {
        // GIVEN
        given(userRepository.findByUsername(users.getUsername())).willReturn(Optional.of(users));

        // WHEN
        Optional<Users> result = userRepositoryService.findByUsername(users.getUsername());

        // THEN
        assertNotNull(result);
        assertEquals(users.getUsername(), result.get().getUsername());
    }
}
