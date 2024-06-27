package com.users.users.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.users.users.dtos.UserAuthorizationToken;
import com.users.users.security.JwtTokenProvider;


@Service
public class UserAuthService {

    // The JSW Token is for user interaction
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // checks the JWT token
    public Optional<UserAuthorizationToken> checkUserAuthToken(String token) {
        // Valida o token com o provider
        if(jwtTokenProvider.validateToken(token)) {
            String profile = jwtTokenProvider.getProfileFromToken(token);
            LocalDateTime expiration = jwtTokenProvider.getExpirationFromToken(token);
            return Optional.of(new UserAuthorizationToken(token, expiration, profile));
        } else {
            // todo fetch the user from the database and set it as unauthorized

            return Optional.empty();
        }

    }

}
