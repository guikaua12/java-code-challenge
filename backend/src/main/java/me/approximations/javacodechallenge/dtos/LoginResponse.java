package me.approximations.javacodechallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private final TokenResponse token;
    private final UsuarioDTO user;
}
