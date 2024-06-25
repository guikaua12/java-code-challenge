package me.approximations.javacodechallenge.services;

import me.approximations.javacodechallenge.entities.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DepartamentoService {
    Page<Departamento> getAll(Pageable pageable);

    Optional<Departamento> findById(Long id);
}
