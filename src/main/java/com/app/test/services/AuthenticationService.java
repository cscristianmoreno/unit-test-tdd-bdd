package com.app.test.services;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.test.dto.LoginDTO;
import com.app.test.models.AuthenticationServiceModel;
import com.app.test.security.CustomAuthenticationManager;

@Service
public class AuthenticationService implements AuthenticationServiceModel {
    private final CustomAuthenticationManager customAuthenticationManager;

    public AuthenticationService(final CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    @Override
    public Authentication authenticate(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            username, password
        );

        Authentication authentication = customAuthenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return authentication;
    }


}
