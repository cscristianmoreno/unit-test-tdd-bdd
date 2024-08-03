package com.app.test.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.test.dto.LoginDTO;
import com.app.test.services.AuthenticationService;
import com.app.test.services.UserRepositoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = AuthController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login() throws Exception {
        // GIVEN
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("test");
        loginDTO.setPassword("test");

        // WHEN
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/auth/login");

        ResultActions resultActions = mockMvc.perform(request
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginDTO))
        );

        MvcResult result = resultActions.andReturn();

        // THEN
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());

        assertEquals("asd", result.getResponse().getContentAsString());
    }
}
