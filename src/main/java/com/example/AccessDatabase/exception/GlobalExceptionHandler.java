package com.example.AccessDatabase.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException exception, Model m) {
        m.addAttribute("errorMessage", exception.getMessage());
        exception.printStackTrace();
        return "errorPage";
    }

    @ExceptionHandler(TransactionSystemException.class)
    public String handleException(TransactionSystemException exception, Model m) {
        Throwable cause = exception.getRootCause();
        if (cause instanceof ConstraintViolationException violationException) {

            StringBuilder builder = new StringBuilder("Validation Failed: ");
            violationException.getConstraintViolations().forEach(violation -> {builder.append(violation.getMessage()).append(" ");});

            m.addAttribute("errorMessage", builder.toString());
            return "errorPage";
        }
        m.addAttribute("errorMessage", "Transaction failed due to an unexpected error.");
        return "errorPage";
    }
}
