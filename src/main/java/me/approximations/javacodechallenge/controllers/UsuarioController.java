package me.approximations.javacodechallenge.controllers;

import jakarta.validation.Valid;
import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public interface UsuarioController {
    @PostMapping("/login")
    TokenResponse login(@RequestBody @Valid UsuarioLoginDTO dto);

    @PostMapping("/register")
    TokenResponse register(@RequestBody @Valid RegisterUsuarioDTO dto);

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    UsuarioDTO create(@RequestBody @Valid CreateUsuarioDTO dto);

    @GetMapping("/")
    Page<UsuarioDTO> getAll(Pageable pageable);

    @GetMapping("/{id}")
    UsuarioDTO findById(@PathVariable("id") Long id);

    @PatchMapping("/")
    UsuarioDTO update(@RequestBody @Valid UpdateUsuarioDTO dto, JwtAuthenticationToken authentication);

    @PatchMapping("/password")
    UsuarioDTO updatePassword(@RequestBody @Valid UpdateUsuarioPasswordDTO dto, JwtAuthenticationToken authentication);

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(@PathVariable("id") Long id);
}
