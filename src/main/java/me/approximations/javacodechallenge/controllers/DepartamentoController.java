package me.approximations.javacodechallenge.controllers;

import me.approximations.javacodechallenge.entities.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DepartamentoController {
    @GetMapping("/")
    Page<Departamento> getAll(Pageable pageable);

    @GetMapping("/{id}")
    Departamento findById(@PathVariable("id") Long id);
}
