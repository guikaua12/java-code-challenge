package me.approximations.javacodechallenge.services;

import me.approximations.javacodechallenge.dtos.RegisterUsuarioDTO;
import me.approximations.javacodechallenge.dtos.UpdateUsuarioDTO;
import me.approximations.javacodechallenge.dtos.UpdateUsuarioPasswordDTO;
import me.approximations.javacodechallenge.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {
    /**
     * Registra um novo usúario no sistema.
     *
     * @param dto Um objeto de transferência de dados contendo os detalhes do usuário a ser registrado.
     * @return Um objeto contendo os detalhes do usuário recém-registrado.
     */
    Usuario register(RegisterUsuarioDTO dto);

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
    Usuario findById(Long id);

    /**
     * Encontra um usuário pelo seu ID.
     *
     * @param email O email do usuário a ser encontrado.
     * @return Um Optional de Usuario contendo os detalhes do usuário encontrado.
     */
    Usuario findByEmail(String email);

    /**
     * Atualiza um usuário existente.
     *
     * @param dto Um objeto de transferência de dados contendo os novos detalhes do usuário.
     * @return Um objeto contendo os detalhes do usuário atualizado.
     */
    Usuario update(UpdateUsuarioDTO dto);

    /**
     * Atualiza a senha de um usuário existente.
     *
     * @param dto Um objeto de transferência de dados contendo a nova senha do usuário.
     * @return Um objeto contendo os detalhes do usuário com a senha atualizada.
     */
    Usuario updatePassword(UpdateUsuarioPasswordDTO dto);

    /**
     * Deleta um usuário existente.
     *
     * @param id O id do usuário a ser deletado
     */
    void delete(Long id);
}
