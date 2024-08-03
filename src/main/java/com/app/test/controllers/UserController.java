package com.app.test.controllers;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.test.entity.Users;
import com.app.test.models.controller.UserControllerModel;
import com.app.test.models.repository.UserRepositoryModel;
import com.app.test.services.UserRepositoryService;

@Controller
@ResponseBody
@RequestMapping("/users")
public class UserController implements UserControllerModel {

    private final UserRepositoryService userRepositoryService;

    public UserController(final UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @PostMapping("/save")
    @Override
    public ResponseEntity<Users> save(@RequestBody Users users) {
        userRepositoryService.save(users);
        ResponseEntity<Users> responseEntity = new ResponseEntity<Users>(users, HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/update")
    @Override
    public ResponseEntity<Users> update(@RequestBody Users users) {
        userRepositoryService.update(users);
        ResponseEntity<Users> responseEntity = new ResponseEntity<Users>(users, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Optional<Users>> findById(@PathVariable int id) {
        Optional<Users> users = userRepositoryService.findById(id);
        HttpStatus status = (users.isPresent()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity<Optional<Users>> responseEntity = new ResponseEntity<Optional<Users>>(users, status);
        return responseEntity;
    }

    @GetMapping("/find-by-name")
    @Override
    public ResponseEntity<Optional<Users>> findByName(@RequestParam String name) {
        Optional<Users> users = userRepositoryService.findByName(name);
        HttpStatus status = (users.isPresent()) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        ResponseEntity<Optional<Users>> responseEntity = new ResponseEntity<Optional<Users>>(users, status);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        userRepositoryService.deleteById(id);
        ResponseEntity<Void> responseEntity = new ResponseEntity<Void>(HttpStatus.OK);
        return responseEntity;
    }
}
