package org.swp391.valuationdiamond.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
