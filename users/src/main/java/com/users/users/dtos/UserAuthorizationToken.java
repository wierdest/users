package com.users.users.dtos;

import java.time.LocalDateTime;

public record UserAuthorizationToken(String token, LocalDateTime expiresAt, String type) {}