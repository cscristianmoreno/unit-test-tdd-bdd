package com.app.test.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.test.entity.Users;
import com.app.test.services.UserRepositoryService;

@Service
public class CustomUserDetatilsService implements UserDetailsService {

    private final UserRepositoryService userRepositoryService;

    public CustomUserDetatilsService(final UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepositoryService.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        return new CustomUserDetails(user.get());
    }
    
}
