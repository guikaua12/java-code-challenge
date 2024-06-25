package me.approximations.javacodechallenge.handler.exception;

import lombok.Getter;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;

@Getter
public class RoleNotFoundException extends NotFoundException {

    public RoleNotFoundException() {
        super(ErrorEnum.ROLE_NOT_FOUND.getMessage());
    }
}
