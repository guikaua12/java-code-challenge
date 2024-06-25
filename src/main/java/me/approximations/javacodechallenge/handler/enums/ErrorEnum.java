package me.approximations.javacodechallenge.handler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorEnum {
    USER_NOT_FOUND(404, "Usuário não encontrado."),
    DEPARTMENT_NOT_FOUND(404, "Departamento não encontrado."),
    ROLE_NOT_FOUND(404, "Cargo não encontrado."),
    BAD_PASSWORD(401, "Senha incorreta.");

    final int code;
    final HttpStatus statusCode = HttpStatus.valueOf(code);
    final String message;

    public static final String INVALID_CPF_MESSAGE = "CPF inválido.";
    public static final String INVALID_EMAIL_MESSAGE = "Email inválido.";
    public static final String AT_LEAST_ONE_NOT_NULL_MESSAGE = "Pelo menos uma propriedade deve ser definida.";
    public static final String NOT_NULL_MESSAGE = " não pode vazio.";
}
