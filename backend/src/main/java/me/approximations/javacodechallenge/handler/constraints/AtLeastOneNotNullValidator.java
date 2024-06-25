package me.approximations.javacodechallenge.handler.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.stream.Stream;

public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Object> {
    private String[] fieldNames;

    @Override
    public void initialize(AtLeastOneNotNull constraintAnnotation) {
        this.fieldNames = constraintAnnotation.fieldNames();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        final BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        return Stream.of(fieldNames)
                .anyMatch(fieldName -> wrapper.getPropertyValue(fieldName) != null);
    }
}