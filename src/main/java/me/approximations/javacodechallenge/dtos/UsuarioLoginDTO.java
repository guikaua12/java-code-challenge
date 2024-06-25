package me.approximations.javacodechallenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;

public record UsuarioLoginDTO(
        @NotBlank(message="O email" + ErrorEnum.NOT_NULL_MESSAGE) @Email(message=ErrorEnum.INVALID_EMAIL_MESSAGE) String email,
        @NotBlank(message="A senha " + ErrorEnum.NOT_NULL_MESSAGE) String password) {
}