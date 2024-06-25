package me.approximations.javacodechallenge.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public interface UsuarioController {
    @Operation(summary="Autentica um usuário e retorna um token JWT.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o token"),
            @ApiResponse(responseCode="401", description="Credenciais inválidas"),
    })
    @PostMapping("/login")
    TokenResponse login(@RequestBody @Valid UsuarioLoginDTO dto);

    @Operation(summary="Registra uma usuário e retorna um token JWT.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o token"),
            @ApiResponse(responseCode="409", description="Email já cadastrado"),
            @ApiResponse(responseCode="404", description="Departamento não encontrado"),
    })
    @PostMapping("/register")
    TokenResponse register(@RequestBody @Valid RegisterUsuarioDTO dto);

    @Operation(summary="Cria um usuário.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o usuário criado"),
            @ApiResponse(responseCode="409", description="Email já cadastrado"),
            @ApiResponse(responseCode="404", description="Departamento não encontrado"),
            @ApiResponse(responseCode="404", description="Cargo não encontrado"),
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN')")
    UsuarioDTO create(@RequestBody @Valid CreateUsuarioDTO dto);

    @Operation(summary="Retorna uma lista paginada de usuários.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna a lista de usuários."),
    })
    @GetMapping("/")
    Page<UsuarioDTO> getAll(Pageable pageable);

    @Operation(summary="Retorna um usuário pelo id.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o usuário"),
            @ApiResponse(responseCode="404", description="Usuário não encontrado"),
    })
    @GetMapping("/{id}")
    UsuarioDTO findById(@PathVariable("id") Long id);

    @Operation(summary="Atualiza os dados de um usuário.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o usuário atualizado"),
            @ApiResponse(responseCode="404", description="Usuário não encontrado"),
            @ApiResponse(responseCode="403", description="Caso o usuário esteja tentando atualizar os dados de outro usuário mas não tem permissão para isso."),
            @ApiResponse(responseCode="401", description="Usuário não autenticado"),
    })
    @PatchMapping("/")
    UsuarioDTO update(@RequestBody @Valid UpdateUsuarioDTO dto, JwtAuthenticationToken authentication);

    @Operation(summary="Atualiza a senha de um usuário.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o usuário atualizado"),
            @ApiResponse(responseCode="404", description="Usuário não encontrado"),
            @ApiResponse(responseCode="403", description="Caso o usuário esteja tentando atualizar a senha de outro usuário mas não tem permissão para isso."),
            @ApiResponse(responseCode="401", description="Usuário não autenticado"),
    })
    @PatchMapping("/password")
    UsuarioDTO updatePassword(@RequestBody @Valid UpdateUsuarioPasswordDTO dto, JwtAuthenticationToken authentication);

    @Operation(summary="Deleta um usuário.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200"),
            @ApiResponse(responseCode="404", description="Usuário não encontrado"),
            @ApiResponse(responseCode="403", description="Usuário não tem permissão."),
            @ApiResponse(responseCode="401", description="Usuário não autenticado"),
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    void delete(@PathVariable("id") Long id);
}
