package me.approximations.javacodechallenge.services.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.entities.Departamento;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.enums.Cargo;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;
import me.approximations.javacodechallenge.handler.exception.DepartmentNotFoundException;
import me.approximations.javacodechallenge.handler.exception.EmailAlreadyExistsException;
import me.approximations.javacodechallenge.handler.exception.RoleNotFoundException;
import me.approximations.javacodechallenge.handler.exception.UserNotFoundException;
import me.approximations.javacodechallenge.repositories.UsuarioRepository;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import me.approximations.javacodechallenge.security.UserAdminRoleChecker;
import me.approximations.javacodechallenge.security.jwt.payload.JwtPayload;
import me.approximations.javacodechallenge.security.jwt.service.JwtService;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import me.approximations.javacodechallenge.services.DepartamentoService;
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
    private final UserAdminRoleChecker userAdminRoleChecker;
    private final DepartamentoService departmentService;

    @Override
    public TokenResponse login(UsuarioLoginDTO dto) {
        final Usuario user = findByEmail(dto.email()).orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException(ErrorEnum.BAD_PASSWORD.getMessage());
        }

        return jwtService.encode(new JwtPayload(user.getId(), user.getEmail()));
    }

    @Override
    public TokenResponse register(RegisterUsuarioDTO dto) {
        checkEmailNotExists(dto.email());

        final String encryptedPassword = passwordEncoder.encode(dto.password());

        /* default role to ADMIN just to make it easier for those who are going to test the code later */
        final Usuario user = new Usuario(null, dto.name(), dto.cpf(), dto.email(), encryptedPassword, Cargo.ADMIN);

        final Departamento department = findDepartmentById(dto.departmentId());
        user.setDepartment(department);

        usuarioRepository.save(user);

        return jwtService.encode(new JwtPayload(user.getId(), user.getEmail()));
    }

    @Override
    public Usuario create(CreateUsuarioDTO dto) {
        checkEmailNotExists(dto.email());

        final Cargo role = Cargo.byName(dto.role());

        if (role == null) {
            throw new RoleNotFoundException();
        }

        final String encryptedPassword = passwordEncoder.encode(dto.password());
        final Usuario user = new Usuario(null, dto.name(), dto.cpf(), dto.email(), encryptedPassword, role);

        final Departamento department = findDepartmentById(dto.departmentId());
        user.setDepartment(department);

        return usuarioRepository.save(user);
    }

    private void checkEmailNotExists(String email) {
        if (usuarioRepository.findByEmail(email).isEmpty()) return;

        throw new EmailAlreadyExistsException();
    }

    private Departamento findDepartmentById(Long id) {
        if (id == null) return null;

        return departmentService.findById(id).orElseThrow(DepartmentNotFoundException::new);
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
    public Usuario update(UpdateUsuarioDTO dto, JwtAuthenticationToken authentication) {
        userAdminRoleChecker.checkUserPermission(dto.id(), authentication);

        final Usuario user = findById(dto.id()).orElseThrow(UserNotFoundException::new);

        if (dto.name() != null) user.setName(dto.name());
        if (dto.cpf() != null) user.setCpf(dto.cpf());
        if (dto.email() != null) user.setEmail(dto.email());

        return usuarioRepository.save(user);
    }

    @Override
    public Usuario updatePassword(UpdateUsuarioPasswordDTO dto, JwtAuthenticationToken authentication) {
        userAdminRoleChecker.checkUserPermission(dto.id(), authentication);

        final Usuario user = findById(dto.id()).orElseThrow(UserNotFoundException::new);

        user.setPassword(passwordEncoder.encode(dto.password()));

        return usuarioRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        final Usuario user = findById(id).orElseThrow(UserNotFoundException::new);
        usuarioRepository.delete(user);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Usuario user = findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new CustomUserDetails(user);
    }
}

