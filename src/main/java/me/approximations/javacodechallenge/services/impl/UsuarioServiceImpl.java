package me.approximations.javacodechallenge.services.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.dtos.RegisterUsuarioDTO;
import me.approximations.javacodechallenge.dtos.UpdateUsuarioDTO;
import me.approximations.javacodechallenge.dtos.UpdateUsuarioPasswordDTO;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.handler.exception.NotFoundException;
import me.approximations.javacodechallenge.repositories.UsuarioRepository;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import me.approximations.javacodechallenge.services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario register(RegisterUsuarioDTO dto) {
        final Usuario user = new Usuario(null, dto.name(), dto.cpf(), dto.email(), dto.password());
        return usuarioRepository.save(user);
    }

    @Override
    public Page<Usuario> getAll(Pageable pageable) {
        return usuarioRepository.getAll(pageable);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario update(UpdateUsuarioDTO dto) {
        final Usuario user = findById(dto.id()).orElseThrow(() -> new NotFoundException("User not found."));

        user.setName(dto.name());
        user.setCpf(dto.cpf());
        user.setEmail(dto.email());

        return usuarioRepository.save(user);
    }

    @Override
    public Usuario updatePassword(UpdateUsuarioPasswordDTO dto) {
        final Usuario user = findById(dto.id()).orElseThrow(() -> new NotFoundException("User not found."));

        user.setPassword(passwordEncoder.encode(dto.password()));

        return usuarioRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        final Usuario user = findById(id).orElseThrow(() -> new NotFoundException("User not found."));
        usuarioRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Usuario user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new CustomUserDetails(user);
    }
}

