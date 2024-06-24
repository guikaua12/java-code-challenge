package me.approximations.javacodechallenge.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterUsuarioDTO(@NotBlank String name, @CPF String cpf, @Email String email,
                                 @NotBlank String password) {
}
