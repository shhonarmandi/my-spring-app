package com.example.demo.exception;

import com.example.demo.dto.ApiErrorResponse;
import com.example.demo.dto.Errors;
import com.example.demo.dto.Target;
import com.example.demo.util.ResponseUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.*;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

// TODO: make trace value dynamic
@RestControllerAdvice
public class MvcExceptionHandler {
  // 400 status code - body validation - handled by jakarta
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleBodyValidation(MethodArgumentNotValidException ex) {
    List<Errors> list =
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fieldError ->
                    new Errors(
                        mapValidationCode(fieldError.getCode()),
                        Optional.ofNullable(fieldError.getDefaultMessage())
                            .orElse("Invalid value."),
                        new Target("field", fieldError.getField())))
            .collect(Collectors.toList());

    ApiErrorResponse body = new ApiErrorResponse(list, "94f7f3e7a6e2");

    return ResponseUtil.error(HttpStatus.BAD_REQUEST, body);
  }

  // 400 status code - missing required cookie
  @ExceptionHandler(MissingRequestCookieException.class)
  public ResponseEntity<ApiErrorResponse> handleMissingCookie(MissingRequestCookieException ex) {
    var error =
        new Errors(
            mapValidationCode("MissingRequestCookie"),
            "Missing cookie '" + ex.getCookieName() + "'",
            new Target("cookie", ex.getCookieName()));

    ApiErrorResponse body = new ApiErrorResponse(List.of(error), "94f7f3e7a6e2");
    return ResponseUtil.error(HttpStatus.BAD_REQUEST, body);
  }

  // 401 status code
  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<ApiErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
    var error = new Errors(ex.getCode(), ex.getDescription(), ex.getTarget());

    return ResponseUtil.error(
        HttpStatus.UNAUTHORIZED, new ApiErrorResponse(List.of(error), "94f7f3e7a6e2"));
  }

  // 404 status code
  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<ApiErrorResponse> handleNoHandler() {
    var error =
        new Errors("not_found", "Resource not found.", new Target("path", "/v1/does-not-exist"));

    return ResponseUtil.error(
        HttpStatus.NOT_FOUND, new ApiErrorResponse(List.of(error), "94f7f3e7a6e2"));
  }

  // TODO: make message argument dynamic
  // 405 status code
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiErrorResponse> handleMethodNotAllowed() {
    var error =
        new Errors(
            "method_not_allowed", "Use GET for this endpoint.", new Target("method", "POST"));

    return ResponseUtil.error(
        HttpStatus.METHOD_NOT_ALLOWED, new ApiErrorResponse(List.of(error), "94f7f3e7a6e2"));
  }

  // TODO: what if user enters an invalid json format
  // 500 status code
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGenericException() {
    var error = new Errors("internal_server_error", "Unexpected error.");

    return ResponseUtil.error(
        HttpStatus.INTERNAL_SERVER_ERROR, new ApiErrorResponse(List.of(error), "94f7f3e7a6e2"));
  }

  // Map Bean Validation codes to IBM-style snake_case error codes
  private String mapValidationCode(String beanValidationCode) {
    if (beanValidationCode == null) return "invalid_parameter";
    return switch (beanValidationCode) {
      case "NotNull", "NotBlank", "NotEmpty" -> "missing_field";
      case "Email" -> "invalid_format";
      case "Pattern" -> "invalid_format";
      case "Size" -> "invalid_length";
      case "Min", "Max", "DecimalMin", "DecimalMax" -> "out_of_range";
      case "Past", "PastOrPresent", "Future", "FutureOrPresent" -> "invalid_date";
      default -> "invalid_parameter";
    };
  }
}
