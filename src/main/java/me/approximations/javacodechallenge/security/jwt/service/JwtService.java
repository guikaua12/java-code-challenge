package me.approximations.javacodechallenge.security.jwt.service;

import me.approximations.javacodechallenge.security.jwt.payload.JwtPayload;

public interface JwtService {
    String encode(JwtPayload payload);

    JwtPayload decode(String token);
}
