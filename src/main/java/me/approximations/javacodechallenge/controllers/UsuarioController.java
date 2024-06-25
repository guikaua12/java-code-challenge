package me.approximations.javacodechallenge.controllers;

import jakarta.validation.Valid;
import me.approximations.javacodechallenge.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

public interface UsuarioController {
    @PostMapping("/login")
    TokenResponse login(@RequestBody @Valid UsuarioLoginDTO dto);

    @PostMapping("/register")
    UsuarioDTO register(@RequestBody @Valid RegisterUsuarioDTO dto);

    @GetMapping("/")
    Page<UsuarioDTO> getAll(Pageable pageable);

    @GetMapping("/{id}")
    UsuarioDTO findById(@PathVariable("id") Long id);

    @PatchMapping("/")
    UsuarioDTO update(@RequestBody @Valid UpdateUsuarioDTO dto);

    @PatchMapping("/password")
    UsuarioDTO updatePassword(UpdateUsuarioPasswordDTO dto);

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") Long id);
}
