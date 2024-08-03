package com.app.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.app.test.dto.LoginDTO;
import com.app.test.security.CustomAuthenticationManager;

import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private CustomAuthenticationManager customAuthenticationManager;
    
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void authenticate() {
        // GIVEN
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("user", "user");
        given(customAuthenticationManager.authenticate(user)).willReturn(user);
        
        // WHEN
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("user");
        loginDTO.setPassword("user");

        Authentication authentication = authenticationService.authenticate(loginDTO);

        // THEN
        assertNotNull(authentication);
        assertEquals(user.getName(), loginDTO.getUsername());
        assertEquals(UsernamePasswordAuthenticationToken.class, authentication.getClass());
    }
}
