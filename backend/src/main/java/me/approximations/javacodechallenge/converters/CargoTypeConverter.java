package me.approximations.javacodechallenge.converters;

import jakarta.persistence.AttributeConverter;
import me.approximations.javacodechallenge.enums.Cargo;

public class CargoTypeConverter implements AttributeConverter<Cargo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Cargo type) {
        if (type == null) {
            return null;
        }

        return type.getId();
    }

    @Override
    public Cargo convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        for (Cargo type : Cargo.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown id: " + id);
    }
}