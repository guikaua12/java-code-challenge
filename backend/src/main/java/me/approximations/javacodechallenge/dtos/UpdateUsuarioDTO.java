package me.approximations.javacodechallenge.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import me.approximations.javacodechallenge.handler.constraints.AtLeastOneNotNull;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;
import org.hibernate.validator.constraints.br.CPF;

@AtLeastOneNotNull(fieldNames={"name", "cpf", "email"}, message=ErrorEnum.AT_LEAST_ONE_NOT_NULL_MESSAGE)
public record UpdateUsuarioDTO(@NotNull(message="O id" + ErrorEnum.NOT_NULL_MESSAGE) Long id, @Nullable String name,
                               @Nullable @CPF(message=ErrorEnum.INVALID_CPF_MESSAGE) String cpf,
                               @Nullable @Email String email) {
}
