
package com.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) @Valid failures on @RequestBody DTO (like CreateOrderRequest)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    // 2) JSON parse / binding errors
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Invalid request body");
        body.put("detail", ex.getMostSpecificCause().getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    // 3) Missing headers (e.g. X-USER-ID not present)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<?> handleMissingHeader(MissingRequestHeaderException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Missing request header");
        body.put("header", ex.getHeaderName());
        return ResponseEntity.badRequest().body(body);
    }

    // 4) Explicit ResponseStatusException from services
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("status", ex.getStatusCode().value());
//        body.put("error", ex.getReason());
//        return ResponseEntity.status(ex.getStatusCode()).body(body);
//    }
}
