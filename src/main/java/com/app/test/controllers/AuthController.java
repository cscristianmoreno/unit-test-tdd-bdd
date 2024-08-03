package com.app.test.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.test.dto.LoginDTO;
import com.app.test.models.controller.AuthControllerModel;
import com.app.test.services.AuthenticationService;

@Controller
@ResponseBody
@RequestMapping("/auth")
public class AuthController implements AuthControllerModel {

    private final AuthenticationService authenticationService;

    public AuthController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @Override
    public String login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationService.authenticate(loginDTO);
        return "asd";
    }
    
}
