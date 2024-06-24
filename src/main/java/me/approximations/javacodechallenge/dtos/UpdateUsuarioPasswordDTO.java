package me.approximations.javacodechallenge.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUsuarioPasswordDTO(@NotNull Long id, @NotBlank String password) {
}
