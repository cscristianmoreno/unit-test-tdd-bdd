package com.app.test.template;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.test.entity.Authority;
import com.app.test.entity.Users;
import com.app.test.roles.Roles;
import com.app.test.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RestControllerTemplate {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Users users;

    @BeforeEach
    void setup() {
        List<Authority> authorities = new ArrayList<Authority>();
        
        for (Roles role: Roles.values()) {
            Authority authority = new Authority();
            authority.setRoles(role);
            authorities.add(authority);
        }

        users = new Users();
        users.setId(1);
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setPassword("test");
        users.setAuthority(authorities);
    }   

    @Test
    void save() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/users/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(users));
        
        
        ResultActions resultActions =  mockMvc.perform(request);

        resultActions
        .andDo(MockMvcResultHandlers.log())
        .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void update() throws Exception {
        users.setName("Pablo");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/users/update");

        ResultActions resultActions = mockMvc.perform(request
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(users))
        );

        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Pablo"));
    }

    @Test
    void findById() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/users/{id}", users.getId());
    
        ResultActions resultActions = mockMvc.perform(request);
        
        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(users.getId()));
    }

    @Test
    void findByName() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/users");
        request.param("name", users.getUsername());

        ResultActions resultActions = mockMvc.perform(request);

        resultActions
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(users.getUsername()));
        
    }
}
