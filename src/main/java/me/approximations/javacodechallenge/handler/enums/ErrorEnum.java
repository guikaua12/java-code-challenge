package me.approximations.javacodechallenge.handler.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorEnum {
    USER_NOT_FOUND(404, "Usuário não encontrado.");

    final int code;
    final String message;
}
