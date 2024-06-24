package me.approximations.javacodechallenge.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import me.approximations.javacodechallenge.handler.constraints.AtLeastOneNotNull;
import org.hibernate.validator.constraints.br.CPF;

@AtLeastOneNotNull(fieldNames={"name", "cpf", "email"})
public record UpdateUsuarioDTO(@NotNull Long id, String name, @Nullable @CPF String cpf,
                               @Nullable @Email String email) {
}
