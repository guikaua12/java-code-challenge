package me.approximations.javacodechallenge.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.approximations.javacodechallenge.entities.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DepartamentoController {
    @Operation(summary="Retorna uma lista de departamentos.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o response"),
    })
    @GetMapping("/")
    Page<Departamento> getAll(Pageable pageable);

    @Operation(summary="Retorna um departamento pelo ID.")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", description="Retorna o response"),
    })
    @GetMapping("/{id}")
    Departamento findById(@PathVariable("id") Long id);
}
