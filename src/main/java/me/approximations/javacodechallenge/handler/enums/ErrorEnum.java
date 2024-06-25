package me.approximations.javacodechallenge.handler.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorEnum {
    USER_NOT_FOUND(404, "Usuário não encontrado.");

    final int code;
    final String message;

    public static final String INVALID_CPF_MESSAGE = "CPF inválido.";
    public static final String INVALID_EMAIL_MESSAGE = "Email inválido.";
    public static final String AT_LEAST_ONE_NOT_NULL_MESSAGE = "Pelo menos uma propriedade deve ser definida.";
    public static final String NOT_NULL_MESSAGE = " não pode vazio.";
}
