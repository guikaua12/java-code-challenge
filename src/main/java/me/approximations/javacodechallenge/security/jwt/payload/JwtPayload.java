package me.approximations.javacodechallenge.security.jwt.payload;

public record JwtPayload(Long id, String email) {
    public static final String EMAIL = "email";
}
