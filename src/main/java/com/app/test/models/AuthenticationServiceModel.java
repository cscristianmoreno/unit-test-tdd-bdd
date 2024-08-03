package com.app.test.models;

import org.springframework.security.core.Authentication;

import com.app.test.dto.LoginDTO;

public interface AuthenticationServiceModel {
    
    Authentication authenticate(LoginDTO loginDTO);
}
