package me.approximations.javacodechallenge.handler.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends StatusCodeException {
    public static final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public NotFoundException(String message) {
        super(STATUS, message);
    }
}
