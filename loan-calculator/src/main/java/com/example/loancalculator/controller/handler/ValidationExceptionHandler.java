package com.example.loancalculator.controller.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidFormatException(MethodArgumentNotValidException exception) {
        String message = exception.getLocalizedMessage().split("default message")[2].replaceAll("\\[|]", " ");
        log.error("Request params data exception: {}", message);
        return ResponseEntity.badRequest().body(message);
    }

}
