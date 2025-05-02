package ru.itmo.app.validation;

import java.math.BigDecimal;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ScaleValidator implements ConstraintValidator<Scale, Double> {
    private Scale annotation;
    public void initialize(Scale annotation) {
        this.annotation = annotation;
    }
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return BigDecimal.valueOf(value).scale() == annotation.value();
    }
}
