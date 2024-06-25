package me.approximations.javacodechallenge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Cargo {
    MEMBER(1),
    ADMIN(2);

    final int id;
}
