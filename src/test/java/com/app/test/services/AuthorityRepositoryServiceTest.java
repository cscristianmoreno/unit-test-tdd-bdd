package com.app.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.test.entity.Authority;
import com.app.test.entity.Users;
import com.app.test.repository.AuthorityRepository;
import com.app.test.repository.UserRepository;
import com.app.test.roles.Roles;

@ExtendWith(MockitoExtension.class)
public class AuthorityRepositoryServiceTest {

    @Mock
    private AuthorityRepository authorityRepository;

    @InjectMocks
    private AuthorityRepositoryService authorityRepositoryService;

    private Authority authority;

    @BeforeEach
    void setup() {
        authority = new Authority();
        authority.setId(1);
        authority.setRoles(Roles.ROLE_SAVE);
    }

    @Test
    void save() {
        // GIVEN
        given(authorityRepository.save(authority)).willReturn(authority);

        // WHEN
        Authority result = authorityRepositoryService.save(authority);

        // THEN
        assertNotNull(result);
        assertEquals(authority, result);
    };

    @Test
    void update() {
        // GIVEN
        given(authorityRepository.save(authority)).willReturn(authority);

        // WHEN
        Authority result = authorityRepositoryService.save(authority);

        // THEN
        assertNotNull(result);
        verify(authorityRepository).save(authority);
    };

    @Test
    void findById() {
        // GIVEN
        given(authorityRepository.findById(authority.getId())).willReturn(Optional.of(authority));

        // WHEN
        Optional<Authority> result = authorityRepositoryService.findById(authority.getId());

        // THEN
        assertNotNull(result);
        assertTrue(result.isPresent());
        verify(authorityRepository).findById(authority.getId());
    };

    @Test
    void deleteById() {
        // GIVEN
        willDoNothing().given(authorityRepository).deleteById(authority.getId());

        // WHEN
        authorityRepositoryService.deleteById(authority.getId());

        // THEN
        verify(authorityRepository).deleteById(authority.getId());
    };
}
