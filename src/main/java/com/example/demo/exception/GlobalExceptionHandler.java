package com.example.demo.exception;

import com.example.demo.dto.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Map<String, Object>>> handleIllegalArgument(
      IllegalArgumentException ex) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                "invalid request: " + ex.getMessage(),
                null,
                true));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(), false, "validation failed", errors, true));
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(
      ResponseStatusException ex) {
    var status = ex.getStatusCode();
    return ResponseEntity.status(status)
        .body(new ApiResponse<>(status.value(), false, ex.getReason(), null, true));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), false, ex.getMessage(), null, true));
  }
}
