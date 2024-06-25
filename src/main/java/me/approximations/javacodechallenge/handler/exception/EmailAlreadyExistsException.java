package me.approximations.javacodechallenge.handler.exception;

import lombok.Getter;
import lombok.Setter;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;

@Getter
@Setter
public class EmailAlreadyExistsException extends StatusCodeException {
    public EmailAlreadyExistsException() {
        super(ErrorEnum.EMAIL_ALREADY_EXISTS.getStatusCode(), ErrorEnum.EMAIL_ALREADY_EXISTS.getMessage());
    }
}
