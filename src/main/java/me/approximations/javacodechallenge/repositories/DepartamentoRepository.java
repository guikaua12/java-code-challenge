package me.approximations.javacodechallenge.repositories;

import me.approximations.javacodechallenge.entities.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    @Query("SELECT d FROM Departamento d")
    Page<Departamento> getAll(Pageable pageable);

    @Query("SELECT d FROM Departamento d WHERE d.id = :id")
    Optional<Departamento> findById(Long id);
}
