package com.example.demo.exception;

import com.example.demo.dto.ApiResponse;
import com.example.demo.util.ResponseUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class MvcExceptionHandler {
  // 400 status code - for Get request - handled by Jakarta dependency
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleBind(BindException ex) {
    var errors = new HashMap<String, String>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
    return ResponseUtil.error(HttpStatus.BAD_REQUEST, "validation failed", errors);
  }

  // 400 status code - for Post request - handled by Jakarta dependency
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

    return ResponseUtil.error(HttpStatus.BAD_REQUEST, "validation failed", errors);
  }

  // 401 status code
  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ApiResponse<Void>> handleInvalidCredentials() {
    return ResponseUtil.error(HttpStatus.UNAUTHORIZED, "invalid credentials");
  }

  // 404 status code - endpoint not found
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNoHandler(NoHandlerFoundException ex) {
    return ResponseUtil.error(HttpStatus.NOT_FOUND, "not found");
  }

  // 405 status code
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodNotAllowed(
      HttpRequestMethodNotSupportedException ex) {
    return ResponseUtil.error(HttpStatus.METHOD_NOT_ALLOWED, "method not allowed");
  }

  // 500 status code
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
    return ResponseUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
  }
}
