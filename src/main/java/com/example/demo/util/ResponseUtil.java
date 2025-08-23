package com.example.demo.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ApiErrorResponse;

public class ResponseUtil {
  public static ResponseEntity<Void> success() {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  public static <T> ResponseEntity<T> success(T data) {
    return ResponseEntity.ok(data);
  }

  public static <T> ResponseEntity<T> success(T data, ResponseCookie... cookies) {
    var builder = ResponseEntity.ok();

    for (var cookie : cookies) {
      builder.header(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    return builder.body(data);
  }

  public static ResponseEntity<ApiErrorResponse> error(
      HttpStatus status, ApiErrorResponse payload) {
    return ResponseEntity.status(status).body(payload);
  }

  public static ResponseEntity<ApiErrorResponse> error(
      HttpStatusCode status, ApiErrorResponse payload) {
    return ResponseEntity.status(status).body(payload);
  }
}
