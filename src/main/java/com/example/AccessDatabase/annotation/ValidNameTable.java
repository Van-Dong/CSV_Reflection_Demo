package com.example.AccessDatabase.annotation;

import com.example.AccessDatabase.validator.NameTableValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NameTableValidator.class)
public @interface ValidNameTable {
    String[] acceptedValues() default {};
    String message() default "Invalid name table";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
