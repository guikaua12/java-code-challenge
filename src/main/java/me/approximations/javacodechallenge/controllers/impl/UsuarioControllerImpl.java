package me.approximations.javacodechallenge.controllers.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.controllers.UsuarioController;
import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.handler.exception.NotFoundException;
import me.approximations.javacodechallenge.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioControllerImpl implements UsuarioController {
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    @Override
    public TokenResponse login(UsuarioLoginDTO dto) {
        return usuarioService.login(dto);
    }

    @Override
    public UsuarioDTO register(RegisterUsuarioDTO dto) {
        final Usuario user = usuarioService.register(dto);

        return modelMapper.map(user, UsuarioDTO.class);
    }

    @Override
    public Page<UsuarioDTO> getAll(Pageable pageable) {
        final Page<Usuario> users = usuarioService.getAll(pageable);

        return users.map(user -> modelMapper.map(user, UsuarioDTO.class));
    }

    @Override
    public UsuarioDTO findById(Long id) {
        final Usuario user = usuarioService.findById(id).orElseThrow(() -> new NotFoundException("User not found."));

        return modelMapper.map(user, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO update(@RequestBody @Valid UpdateUsuarioDTO dto) {
        return modelMapper.map(usuarioService.update(dto), UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO updatePassword(UpdateUsuarioPasswordDTO dto) {
        return modelMapper.map(usuarioService.updatePassword(dto), UsuarioDTO.class);
    }

    @Override
    public void delete(Long id) {
        usuarioService.delete(id);
    }
}
