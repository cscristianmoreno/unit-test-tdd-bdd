package com.app.test.services;

import java.util.Optional;

import com.app.test.entity.Authority;
import com.app.test.models.repository.AuthorityRepositoryModel;
import com.app.test.repository.AuthorityRepository;

public class AuthorityRepositoryService implements AuthorityRepositoryModel {

    private final AuthorityRepository authorityRepository;

    public AuthorityRepositoryService(final AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority save(Authority entity) {
        return authorityRepository.save(entity);
    }

    @Override
    public Authority update(Authority entity) {
        return authorityRepository.save(entity);
    }

    @Override
    public Optional<Authority> findById(int id) {
        return authorityRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        authorityRepository.deleteById(id);
    }
    
}
