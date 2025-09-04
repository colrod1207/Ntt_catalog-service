package org.taller01.catalogservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.taller01.catalogservice.dto.ErrorResponse;
import org.taller01.catalogservice.dto.ValidationErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex, HttpServletRequest req) {
        var body = new ErrorResponse(
                404, "Not Found", ex.getMessage(), req.getRequestURI(), Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, HttpServletRequest req) {
        var body = new ErrorResponse(
                400, "Bad Request", ex.getMessage(), req.getRequestURI(), Instant.now()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                                    HttpServletRequest req) {
        Map<String, String> fields = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(fe -> fields.put(fe.getField(), fe.getDefaultMessage()));

        var body = new ValidationErrorResponse(
                400, "Validation Failed", "Hay errores de validación",
                req.getRequestURI(), Instant.now(), fields
        );
        return ResponseEntity.badRequest().body(body);
    }
}
