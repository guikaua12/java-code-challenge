package me.approximations.javacodechallenge.controllers.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.controllers.UsuarioController;
import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.handler.exception.UserNotFoundException;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import me.approximations.javacodechallenge.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioControllerImpl implements UsuarioController {
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    @Override
    public LoginResponse login(UsuarioLoginDTO dto) {
        return usuarioService.login(dto);
    }

    @Override
    public LoginResponse register(RegisterUsuarioDTO dto) {
        return usuarioService.register(dto);
    }

    @Override
    public UsuarioDTO create(CreateUsuarioDTO dto) {
        return modelMapper.map(usuarioService.create(dto), UsuarioDTO.class);
    }

    @Override
    public Page<UsuarioDTO> getAll(Pageable pageable) {
        final Page<Usuario> users = usuarioService.getAll(pageable);

        return users.map(user -> modelMapper.map(user, UsuarioDTO.class));
    }

    @Override
    public UsuarioDTO findById(Long id) {
        final Usuario user = usuarioService.findById(id).orElseThrow(UserNotFoundException::new);

        return modelMapper.map(user, UsuarioDTO.class);
    }

    @Override
    public Usuario findByToken(String token) {
        return usuarioService.findByToken(token);
    }

    @Override
    public UsuarioDTO update(@RequestBody @Valid UpdateUsuarioDTO dto, JwtAuthenticationToken authentication) {


        return modelMapper.map(usuarioService.update(dto, authentication), UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO updatePassword(UpdateUsuarioPasswordDTO dto, JwtAuthenticationToken authentication) {
        return modelMapper.map(usuarioService.updatePassword(dto, authentication), UsuarioDTO.class);
    }

    @Override
    public void delete(Long id) {
        usuarioService.delete(id);
    }

}
