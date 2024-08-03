package com.app.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.test.entity.Users;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<Users, Integer> {
    Optional<Users> findById(int id);

    Optional<Users> findByName(String name);

    Optional<Users> findByUsername(String username);
}
