package com.app.test.security;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.test.entity.Users;
import com.app.test.services.UserRepositoryService;

@ExtendWith(MockitoExtension.class)
public class CustomAuthenticationProviderTest {

    @Mock
    private CustomUserDetatilsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private CustomAuthenticationProvider customAuthenticationProvider;
    
    private Users users;
    private UserDetails userDetails;
    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;
    private PasswordEncoder passwordEncoderFactories;
    
    @BeforeEach
    void setup() {
        passwordEncoderFactories = new BCryptPasswordEncoder();

        users = new Users();
        users.setId(1);
        users.setUsername("user");
        users.setPassword(passwordEncoderFactories.encode("user"));

        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            "user",
            "user"
        );

        userDetails = new CustomUserDetails(users);
    }
    
    @Test
    void authenticate() {
        // GIVEN
        given(userDetailsService.loadUserByUsername(users.getUsername())).willReturn(userDetails);
        given(passwordEncoder.matches("user", users.getPassword())).willReturn(true);

        // WHEN
        Authentication authentication = customAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);

        Set<String> compare = Set.of("ROLE_ADMIN");
        Set<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        // THEN
        assertNotNull(authentication);
        assertNotNull(authentication.getAuthorities());
        assertEquals(compare, authorities);
    }

    @Test
    void authenticateWithPasswordFailed() {
        // GIVEN
        given(userDetailsService.loadUserByUsername(users.getUsername())).willReturn(userDetails);
        given(passwordEncoder.matches("user", users.getPassword())).willReturn(false);
        
        // WHEN
        assertThrows(BadCredentialsException.class, () -> {
            customAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        });
    }
}
