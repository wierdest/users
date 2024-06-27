package com.users.users.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.users.users.domain.User;
import com.users.users.domain.UserProfile;
import com.users.users.dtos.UserAuthorizationToken;
import com.users.users.dtos.UserDTO;
import com.users.users.dtos.UserLoginDTO;
import com.users.users.repository.UserRepository;
import com.users.users.security.JwtTokenProvider;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable).map(UserDTO::toDTO);
    }

    public UserAuthorizationToken registerUser(UserLoginDTO dto) {
        // Confere se o email é único
        if(repository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Email já está em uso!");
        }

        // Criptografa o password
        String encryptedPassword = passwordEncoder.encode(dto.password());

        // Cria o usuário 
        User user = new User(
            dto.email(), 
            encryptedPassword, 
            LocalDateTime.now(), 
            UserProfile.fromCode(dto.profileCode())
        );

        user.setAuthenticated(true);
        repository.save(user);

        // Gera o JWT
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getUserProfile().name());

        // Calcula o tempo de expiração do token
        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(jwtTokenProvider.getValidityLimitInMilliseconds() / 1000);

        // Retorna o AuthorizationToken 
        return new UserAuthorizationToken(token, expirationTime, "Bearer");
    }
}
