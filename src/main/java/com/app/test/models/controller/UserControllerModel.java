package com.app.test.models.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.app.test.entity.Users;
import com.app.test.models.repository.UserRepositoryModel;

public interface UserControllerModel {
    ResponseEntity<Users> save(Users users);

    ResponseEntity<Users> update(Users users);

    ResponseEntity<Optional<Users>> findById(int id);

    ResponseEntity<Optional<Users>> findByName(String name);

    ResponseEntity<Void> deleteById(int id);
}
