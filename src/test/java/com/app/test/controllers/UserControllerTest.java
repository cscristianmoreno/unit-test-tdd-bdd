package com.app.test.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.test.entity.Users;
import com.app.test.security.CustomUserDetails;
import com.app.test.security.CustomUserDetatilsService;
import com.app.test.security.SecurityConfig;
import com.app.test.services.UserRepositoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepositoryService userRepositoryService;

    private Users users;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() { 
        users = new Users();
        users.setId(1);
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setPassword("test");
    }
    
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    @Test
    void save() throws JsonProcessingException, Exception {
        // GIVEN
        given(userRepositoryService.save(users)).willReturn(any());
        

        // WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/users/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(users))
        );

        // THEN
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(users.getName())));
    }

    @Test
    void update() throws JsonProcessingException, Exception {
        // GIVEN
        given(userRepositoryService.findById(users.getId())).willReturn(Optional.of(users));
        
        Users updateUser = userRepositoryService.findById(users.getId()).get();
        updateUser.setName("Pablo");
        given(userRepositoryService.update(updateUser)).willReturn(updateUser);

        // WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateUser))
        );

        // THEN
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("Pablo")));
    }
    
    @Test
    void findById() throws Exception {
        // GIVEN
        given(userRepositoryService.findById(users.getId())).willReturn(Optional.of(users));

        // WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", users.getId()));

        // THEN
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(users.getId())));
    }

    @Test
    void findByIdNotFound() throws Exception {
        // GIVEN
        given(userRepositoryService.findById(users.getId())).willReturn(Optional.empty());

        // WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", users.getId()));

        // THEN
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void findByName() throws Exception {
        // GIVEN
        given(userRepositoryService.findByName(users.getName())).willReturn(Optional.of(users));

        // WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/find-by-name")
            .param("name", users.getName())
        );

        // THEN
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(users.getName())));
    }

    @Test
    void deleteById() throws Exception {
        // GIVEN
        willDoNothing().given(userRepositoryService).deleteById(users.getId());

        // WHEN
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", users.getId()));

        // THEN
        verify(userRepositoryService, times(1)).deleteById(users.getId());
        
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
        
    }
}
