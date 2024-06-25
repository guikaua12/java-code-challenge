package me.approximations.javacodechallenge.services.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.enums.Cargo;
import me.approximations.javacodechallenge.handler.exception.NotFoundException;
import me.approximations.javacodechallenge.handler.exception.RoleNotFoundException;
import me.approximations.javacodechallenge.repositories.UsuarioRepository;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import me.approximations.javacodechallenge.security.jwt.payload.JwtPayload;
import me.approximations.javacodechallenge.security.jwt.service.JwtService;
import me.approximations.javacodechallenge.services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public TokenResponse login(UsuarioLoginDTO dto) {
        final Usuario user = findByEmail(dto.email()).orElseThrow(() -> new NotFoundException("User not found."));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("Password does not match.");
        }

        return jwtService.encode(new JwtPayload(user.getId(), user.getEmail()));
    }

    @Override
    public TokenResponse register(RegisterUsuarioDTO dto) {
        final String encryptedPassword = passwordEncoder.encode(dto.password());

        /* default role to ADMIN just to make it easier for those who are going to test the code later */
        final Usuario user = new Usuario(null, dto.name(), dto.cpf(), dto.email(), encryptedPassword, Cargo.ADMIN);
        usuarioRepository.save(user);

        return jwtService.encode(new JwtPayload(user.getId(), user.getEmail()));
    }

    @Override
    public Usuario create(CreateUsuarioDTO dto) {
        final Cargo role = Cargo.byName(dto.role());

        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }

        final String encryptedPassword = passwordEncoder.encode(dto.password());

        final Usuario user = new Usuario(null, dto.name(), dto.cpf(), dto.email(), encryptedPassword, role);
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
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Usuario user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new CustomUserDetails(user);
    }
}

