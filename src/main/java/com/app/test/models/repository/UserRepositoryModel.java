package com.app.test.models.repository;

import java.util.Optional;

import com.app.test.entity.Users;

public interface UserRepositoryModel extends CrudRepositoryModel<Users> {
    Optional<Users> findByName(String name);

    Optional<Users> findByUsername(String username);
}
