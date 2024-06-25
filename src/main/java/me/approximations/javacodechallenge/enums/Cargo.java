package me.approximations.javacodechallenge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Cargo {
    MEMBER(1),
    ADMIN(2);

    final int id;

    public static Cargo byName(String name) {
        if (name == null) return null;

        for (final Cargo value : Cargo.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }

        return null;
    }
}
