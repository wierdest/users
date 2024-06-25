package com.users.users.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.users.dtos.AuthorizationToken;
import com.users.users.services.AuthService;

@RestController
@RequestMapping("/auth-check")
public class AuthController {

    @Autowired
    private AuthService service;

    @GetMapping
    public ResponseEntity<?> checkAuth(@RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Optional<AuthorizationToken> token = service.checkAuthToken(authorizationHeader);
        if(token.isEmpty()) {
            return new ResponseEntity<String>(
                "Não autorizado. Token inválido ou expirado!",
                HttpStatus.UNAUTHORIZED
            );
        }
        return ResponseEntity.ok(token.get());
    }
}
