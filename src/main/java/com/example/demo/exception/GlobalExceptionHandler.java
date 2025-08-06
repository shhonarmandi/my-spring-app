package com.example.demo.exception;

import com.example.demo.dto.ApiResponse;
import com.example.demo.util.ResponseUtil;
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
    return ResponseUtil.error(HttpStatus.BAD_REQUEST, "invalid request: " + ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseUtil.error(HttpStatus.BAD_REQUEST, "validation failed", errors);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(
      ResponseStatusException ex) {
    var status = ex.getStatusCode();

    return ResponseUtil.error(status, ex.getReason());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials(
      InvalidCredentialsException ex) {
    return ResponseUtil.error(HttpStatus.UNAUTHORIZED, ex.getMessage());
  }
}
