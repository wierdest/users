package com.users.users.dtos;

import java.time.LocalDateTime;

import com.users.users.domain.User;

public record UserDTO(
    Long id, 
    String email, 
    LocalDateTime createdAt, 
    LocalDateTime accessedAt, 
    Boolean authenticated,
    String profile
    ) {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
            user.getId(), 
            user.getEmail(),
            user.getCreatedAt(),
            user.getAccessedAt(),
            user.isAuthenticated(),
            user.getUserProfile().name()
        );
    }

}
