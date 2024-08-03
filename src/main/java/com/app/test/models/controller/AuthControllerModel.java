package com.app.test.models.controller;

import com.app.test.dto.LoginDTO;

public interface AuthControllerModel {
    String login(LoginDTO loginDTO);
}
