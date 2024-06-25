package me.approximations.javacodechallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenResponse {
    private final long id;
    private final String email;
    private final String token;
    private final long expiresIn;
}
