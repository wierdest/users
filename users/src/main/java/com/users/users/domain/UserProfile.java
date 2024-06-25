package com.users.users.domain;

public enum UserProfile {
    ADMIN(0),
    RECRUITER(1),
    CANDIDATE(2);

    private final int code;

    UserProfile(int code) {
      this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserProfile fromCode(int code) {
        for (UserProfile profile : UserProfile.values()) {
            if (profile.getCode() == code) {
                return profile;
            }
        }
        throw new IllegalArgumentException("Código inválido para Perfil de Usuário " + code);
    }

}
