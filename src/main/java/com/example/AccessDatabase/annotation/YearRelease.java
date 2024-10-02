package com.example.AccessDatabase.annotation;

import com.example.AccessDatabase.validator.YearReleaseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearReleaseValidator.class)
public @interface YearRelease {
    String message() default "";
    int min() default 1900;
    int max() default 2024;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
