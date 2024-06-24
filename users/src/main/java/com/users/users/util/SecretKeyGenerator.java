package com.users.users.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static String generateSecretKeyBase64() {
        // Generate 256-bit (32-byte) secure random key
        byte[] bytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);

        // Convert bytes to Base64 for storage or printing
        String secretKey = Base64.getEncoder().encodeToString(bytes);
        return secretKey;
    }

    public static void main(String[] args) {
        String secretKey = generateSecretKeyBase64();
        System.out.println("Generated Secret Key: " + secretKey);
    }
}
