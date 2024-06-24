package me.approximations.javacodechallenge.config.security;

import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.security.jwt.filters.JwtAuthenticationFilter;
import me.approximations.javacodechallenge.security.jwt.service.JwtService;
import me.approximations.javacodechallenge.services.UsuarioService;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    public void init(HttpSecurity builder) {
    }

    @Override
    public void configure(HttpSecurity http) {
        final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtService, usuarioService);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
