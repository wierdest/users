package com.users.users.dtos;

import java.time.LocalDateTime;

import com.users.users.domain.User;
import com.users.users.util.Mapper;

public record UserDTO(
    Long id, 
    String email, 
    LocalDateTime createdAt, 
    LocalDateTime accessedAt, 
    Boolean authenticated
    ) {

    public static UserDTO toDTO(User user) {
        return Mapper.getMapper().convertValue(user, UserDTO.class);
    }

}
