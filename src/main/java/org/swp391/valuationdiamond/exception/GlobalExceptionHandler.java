package org.swp391.valuationdiamond.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(value = RuntimeException.class)
        ResponseEntity<String> handleRuntimeException(RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler(value = IllegalArgumentException.class)
        ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> handleValidationExceptions(
                MethodArgumentNotValidException ex, WebRequest request) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
}
