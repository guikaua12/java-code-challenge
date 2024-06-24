package me.approximations.javacodechallenge.repositories;

import me.approximations.javacodechallenge.entities.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("SELECT u FROM Usuario u")
    Page<Usuario> getAll(Pageable pageable);

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    Optional<Usuario> findByEmail(String email);
}
