package com.users.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.users.dtos.AuthorizationToken;
import com.users.users.dtos.UserDTO;
import com.users.users.dtos.UserLoginDTO;
import com.users.users.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        return new ResponseEntity<Page<UserDTO>>(service.getAllUsers(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AuthorizationToken> registerUser(@RequestBody UserLoginDTO dto) {
        AuthorizationToken token = service.registerUser(dto);
        return new ResponseEntity<AuthorizationToken>(token, HttpStatus.CREATED);

    }
}
