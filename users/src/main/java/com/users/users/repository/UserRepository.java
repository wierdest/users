package com.users.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.users.users.domain.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
