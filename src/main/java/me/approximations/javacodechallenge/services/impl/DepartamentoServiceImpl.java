package me.approximations.javacodechallenge.services.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.entities.Departamento;
import me.approximations.javacodechallenge.repositories.DepartamentoRepository;
import me.approximations.javacodechallenge.services.DepartamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartamentoServiceImpl implements DepartamentoService {
    private final DepartamentoRepository departamentoRepository;

    @Override
    public Page<Departamento> getAll(Pageable pageable) {
        return departamentoRepository.getAll(pageable);
    }

    @Override
    public Optional<Departamento> findById(Long id) {
        return departamentoRepository.findById(id);
    }
}
