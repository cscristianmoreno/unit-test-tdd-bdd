package com.app.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.test.entity.Authority;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
    
}
