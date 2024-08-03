package com.app.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.app.test.entity.Authority;
import com.app.test.entity.Users;
import com.app.test.roles.Roles;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private Users users;

    private Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

    @BeforeEach
    void setup() {
        List<Authority> authorities = new ArrayList<Authority>();
        
        for (Roles role: Roles.values()) {
            Authority authority = new Authority();
            authority.setRoles(role);
            authorities.add(authority);
        }

        users = new Users();
        users.setName("Cristian");
        users.setLastname("Moreno");
        users.setUsername("user");
        users.setPassword("user");
        users.setAuthority(authorities);
    }

    @Test
    @Order(1)
    void save() {
        // GIVEN
        given(userRepository.save(users)).willReturn(users);
        // given(userRepository.findById(users.getId())).willReturn(Optional.of(users));

        
        // WHEN
        Users result = userRepository.save(users);
        
        // THEN 
        assertNotNull(result);
        assertEquals(users, result);
    }

    @Test
    @Order(2)
    void update() {
        // GIVEN
        given(userRepository.save(users)).willReturn(users);
        Users result = userRepository.save(users);
        result.setName("Carlos");
        result.setLastname("Escobar");

        given(userRepository.findById(users.getId())).willReturn(Optional.of(result));
        
        // WHEN

        userRepository.save(result);
        Optional<Users> find = userRepository.findById(users.getId());
        Users user = find.get();

        // THEN 
        assertNotNull(user);
        assertEquals("Carlos", user.getName());
        assertEquals("Escobar", user.getLastname());
    }

    @Test
    @Order(3)
    void findById() {
        // GIVEN
        given(userRepository.findById(users.getId())).willReturn(Optional.of(users));

        // WHEN
        Optional<Users> result = userRepository.findById(users.getId());
        System.out.println(result.get());

        // THEN
        assertNotNull(result);
        assertEquals(users, result.get());
    }

    @Test
    @Order(4)
    void findByName() {
        // GIVEN
        given(userRepository.findByName(users.getName())).willReturn(Optional.of(users));

        // WHEN
        Optional<Users> find = userRepository.findByName(users.getName());
        Users user = find.get(); 

        // THEN
        assertNotNull(user);
        assertEquals(users, user);
    }

    @Test
    @Order(5)
    void deleteById() {
        // GIVEN
        willDoNothing().given(userRepository).deleteById(users.getId());

        // WHEN
        userRepository.deleteById(users.getId());

        // THEN
        verify(userRepository).deleteById(users.getId());
    }

    @Test
    @Order(6)
    void findByUsername() {
        // GIVEN
        given(userRepository.findByUsername(users.getUsername())).willReturn(Optional.of(users));

        // WHEN
        Optional<Users> result = userRepository.findByUsername(users.getUsername());

        // THEN
        assertNotNull(result);
        assertEquals(users.getUsername(), result.get().getUsername());
    }
}
