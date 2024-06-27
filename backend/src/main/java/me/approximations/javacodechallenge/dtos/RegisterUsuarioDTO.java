package me.approximations.javacodechallenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterUsuarioDTO(
        @NotBlank(message="O nome" + ErrorEnum.NOT_NULL_MESSAGE) String name,
        @NotBlank(message="O CPF" + ErrorEnum.NOT_NULL_MESSAGE) @CPF(message=ErrorEnum.INVALID_CPF_MESSAGE) String cpf,
        @NotBlank(message="O email" + ErrorEnum.NOT_NULL_MESSAGE) @Email(message=ErrorEnum.INVALID_EMAIL_MESSAGE) String email,
        @NotBlank(message="A senha " + ErrorEnum.NOT_NULL_MESSAGE) String password,
        Long departmentId) {
}