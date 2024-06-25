package me.approximations.javacodechallenge.handler.exception;

import lombok.Getter;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;

@Getter
public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException() {
        super(ErrorEnum.USER_NOT_FOUND.getMessage());
    }
}
