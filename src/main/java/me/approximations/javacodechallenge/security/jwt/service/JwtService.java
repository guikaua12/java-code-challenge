package me.approximations.javacodechallenge.security.jwt.service;

import me.approximations.javacodechallenge.dtos.TokenResponse;
import me.approximations.javacodechallenge.security.jwt.payload.JwtPayload;

public interface JwtService {
    TokenResponse encode(JwtPayload payload);

    JwtPayload decode(String token);
}
