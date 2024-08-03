package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.app.test.entity.Users;
import com.app.test.repository.UserRepository;
import com.app.test.services.UserRepositoryService;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetatilsServiceTest {
    
    @Mock
    private UserRepositoryService userRepositoryService;

    @InjectMocks
    private CustomUserDetatilsService customUserDetatilsService;

    private Users users;

    @BeforeEach
    void setup() {
        users = new Users();
        users.setUsername("user");
        users.setPassword("user");
    }

    @Test
    void loadUserByUsername() {
        // GIVEN
        given(userRepositoryService.findByUsername(users.getUsername())).willReturn(Optional.of(users));

        // WHEN
        UserDetails customUserDetails = customUserDetatilsService.loadUserByUsername(users.getUsername());

        // THEN
        assertNotNull(customUserDetails.getUsername());
        assertEquals(users.getUsername(), customUserDetails.getUsername());
    }
    
    @Test
    void loadUserByUsernameNotFound() {
        // GIVEN
        given(userRepositoryService.findByUsername(users.getUsername())).willReturn(Optional.empty());

        // WHEN
        // NOTHING!

        // THEN
        assertThrows(UsernameNotFoundException.class, () ->{
            customUserDetatilsService.loadUserByUsername(users.getUsername());
        });
    }
}
