package com.example.AccessDatabase.validator;

import com.example.AccessDatabase.annotation.YearRelease;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearReleaseValidator implements ConstraintValidator<YearRelease, Integer> {

    private int min;
    private int max;

    @Override
    public void initialize(YearRelease constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext constraintValidatorContext) {
        return year >= min && year <= max;
    }
}
