package me.approximations.javacodechallenge.handler.exception;

import lombok.Getter;
import me.approximations.javacodechallenge.handler.enums.ErrorEnum;

@Getter
public class DepartmentNotFoundException extends NotFoundException {
    public DepartmentNotFoundException() {
        super(ErrorEnum.DEPARTMENT_NOT_FOUND.getMessage());
    }
}
