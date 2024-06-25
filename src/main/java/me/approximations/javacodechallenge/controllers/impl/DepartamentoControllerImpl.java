package me.approximations.javacodechallenge.controllers.impl;

import lombok.RequiredArgsConstructor;
import me.approximations.javacodechallenge.controllers.DepartamentoController;
import me.approximations.javacodechallenge.entities.Departamento;
import me.approximations.javacodechallenge.handler.exception.NotFoundException;
import me.approximations.javacodechallenge.services.DepartamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/department")
@RestController
@RequiredArgsConstructor
public class DepartamentoControllerImpl implements DepartamentoController {
    private final DepartamentoService departmentService;

    @Override
    public Page<Departamento> getAll(Pageable pageable) {
        return departmentService.getAll(pageable);
    }

    @Override
    public Departamento findById(Long id) {
        return departmentService.findById(id).orElseThrow(() -> new NotFoundException("Department not found."));
    }
}
