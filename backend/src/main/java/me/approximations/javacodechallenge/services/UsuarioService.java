package me.approximations.javacodechallenge.services;

import me.approximations.javacodechallenge.dtos.*;
import me.approximations.javacodechallenge.entities.Usuario;
import me.approximations.javacodechallenge.security.CustomUserDetails;
import me.approximations.javacodechallenge.security.jwt.token.JwtAuthenticationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UsuarioService extends UserDetailsService {
    /**
     * Autentica um usúario no sistema.
     *
     * @param dto Um objeto de transferência de dados contendo os detalhes do usuário a ser autenticado.
     * @return Um objeto TokenResponse contendo um token JWT.
     */
    LoginResponse login(UsuarioLoginDTO dto);

    /**
     * Registra um novo usúario no sistema.
     *
     * @param dto Um objeto de transferência de dados contendo os detalhes do usuário a ser registrado.
     * @return Um objeto TokenResponse contendo um token JWT.
     */
    LoginResponse register(RegisterUsuarioDTO dto);

    /**
     * Cria um novo usúario no sistema.
     *
     * @param dto Um objeto de transferência de dados contendo os detalhes do usuário a ser criado.
     * @return Um objeto Usuario.
     */
    Usuario create(CreateUsuarioDTO dto);

    /**
     * Recupera todos os usuários do sistema em um formato paginado.
     *
     * @param pageable Um objeto Pageable que fornece informações sobre o tamanho e o número da página a ser recuperada.
     * @return Um objeto Page contendo uma lista de Usuario.
     */
    Page<Usuario> getAll(Pageable pageable);

    /**
     * Encontra um usuário pelo seu ID.
     *
     * @param id O ID do usuário a ser encontrado.
     * @return Um Optional de Usuario contendo os detalhes do usuário encontrado.
     */
    Optional<Usuario> findById(Long id);

    /**
     * Encontra um usuário pelo seu ID.
     *
     * @param email O email do usuário a ser encontrado.
     * @return Um Optional de Usuario contendo os detalhes do usuário encontrado.
     */
    Optional<Usuario> findByEmail(String email);

    Usuario findByToken(String token);

    /**
     * Atualiza um usuário existente.
     *
     * @param dto Um objeto de transferência de dados contendo os novos detalhes do usuário.
     * @return Um objeto contendo os detalhes do usuário atualizado.
     */
    Usuario update(UpdateUsuarioDTO dto, JwtAuthenticationToken authentication);

    /**
     * Atualiza a senha de um usuário existente.
     *
     * @param dto Um objeto de transferência de dados contendo a nova senha do usuário.
     * @return Um objeto contendo os detalhes do usuário com a senha atualizada.
     */
    Usuario updatePassword(UpdateUsuarioPasswordDTO dto, JwtAuthenticationToken authentication);

    /**
     * Deleta um usuário existente.
     *
     * @param id O id do usuário a ser deletado
     */
    void delete(Long id);

    @Override
    CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
