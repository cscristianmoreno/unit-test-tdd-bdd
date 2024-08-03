package com.app.test.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.app.test.entity.Authority;
import com.app.test.roles.Roles;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class AuthorityRepositoryTest {
    
    @Mock
    private AuthorityRepository authorityRepository;

    private Authority authority;

    @BeforeEach
    void setup() {
        authority = new Authority();
        authority.setId(1);
        authority.setRoles(Roles.ROLE_READ);
    }

    @Test
    void save() {
        // GIVEN
        given(authorityRepository.save(authority)).willReturn(authority);

        // WHEN
        Authority result = authorityRepository.save(authority);
        System.out.println(result);

        // THEN
        assertNotNull(result);
    }

    @Test
    void update() {
        authority.setRoles(Roles.ROLE_SAVE);

        // GIVEN
        given(authorityRepository.save(authority)).willReturn(authority);

        // WHEN
        Authority result = authorityRepository.save(authority);

        // THEN
        assertNotNull(result);
        assertEquals(Roles.ROLE_SAVE, result.getRoles());
    }

    @Test
    void findById() {
        // GIVEN
        given(authorityRepository.findById(authority.getId())).willReturn(Optional.of(authority));

        // WHEN
        Optional<Authority> result = authorityRepository.findById(authority.getId());

        // THEN
        assertNotNull(result);
        assertTrue(result.isPresent());
    }

    @Test
    void deleteById() {
        // GIVEN
        willDoNothing().given(authorityRepository).deleteById(authority.getId());

        // WHEN
        authorityRepository.deleteById(authority.getId());

        // THEN
        verify(authorityRepository).deleteById(authority.getId());
    }
}
