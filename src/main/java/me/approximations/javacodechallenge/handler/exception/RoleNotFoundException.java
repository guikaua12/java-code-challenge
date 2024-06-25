package me.approximations.javacodechallenge.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoleNotFoundException extends StatusCodeException {
    public static final HttpStatus STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    public RoleNotFoundException(String message) {
        super(STATUS, message);
    }
}
