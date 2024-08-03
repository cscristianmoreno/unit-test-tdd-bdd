package com.app.test.models.repository;

import java.util.Optional;

import com.app.test.entity.Users;

public interface CrudRepositoryModel<T> {
    T save(T entity);

    T update(T entity);

    Optional<T> findById(int id);

    void deleteById(int id);
}
