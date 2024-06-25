package com.users.users.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.users.users.dtos.AuthorizationToken;
import com.users.users.security.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public Optional<AuthorizationToken> checkAuthToken(String token) {
        // Valida o token com o provider
        if(jwtTokenProvider.validateToken(token)) {
            String profile = jwtTokenProvider.getProfileFromToken(token);
            LocalDateTime expiration = jwtTokenProvider.getExpirationFromToken(token);
            return Optional.of(new AuthorizationToken(token, expiration, profile));
        } else {
            return Optional.empty();
        }

    }

}
