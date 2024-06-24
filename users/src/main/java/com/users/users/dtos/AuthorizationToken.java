package com.users.users.dtos;

import java.time.LocalDateTime;

public record AuthorizationToken(String token, LocalDateTime expiresAt, String type) {}