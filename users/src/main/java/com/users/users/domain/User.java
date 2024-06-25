package com.users.users.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime accessedAt;

    @Column(nullable=false)
    private boolean authenticated;

    @Column(nullable=false)
    private int profile;

    public User() {}

    public User(String email, String encryptedPassword, LocalDateTime now, UserProfile profile) {
       this.email = email;
       this.password = encryptedPassword;
       this.createdAt = now;
       this.profile = profile.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAccessedAt() {
        return accessedAt;
    }

    public void setAccessedAt(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public UserProfile getUserProfile() {
        return UserProfile.fromCode(this.profile);
    }

    public void setUserProfile(UserProfile profile) {
        this.profile = profile.getCode();
    }

    public int getProfile() {
        return this.profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

}
    
