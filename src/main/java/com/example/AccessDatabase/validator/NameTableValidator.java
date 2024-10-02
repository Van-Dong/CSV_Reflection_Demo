package com.example.AccessDatabase.validator;

import com.example.AccessDatabase.annotation.ValidNameTable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameTableValidator implements ConstraintValidator<ValidNameTable, String> {
    private String[] acceptedValues;

    @Override
    public void initialize(ValidNameTable constraintAnnotation) {
        this.acceptedValues = constraintAnnotation.acceptedValues();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {return false;}
        for (String table : acceptedValues) {
            if (s.equals(table)) {
                return true;
            }
        }
        return false;
    }
}
