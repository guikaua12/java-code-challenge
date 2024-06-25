package me.approximations.javacodechallenge.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;

public record UpdateUsuarioPasswordDTO(
        @NotNull(message="O id" + ErrorEnum.NOT_NULL_MESSAGE) Long id,
        @NotBlank(message="A senha " + ErrorEnum.NOT_NULL_MESSAGE) String password) {
}