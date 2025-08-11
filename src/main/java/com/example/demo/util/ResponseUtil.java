package com.example.demo.util;

import com.example.demo.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
  public static <T> ResponseEntity<ApiResponse<T>> success() {
    return ResponseEntity.ok(new ApiResponse<>("success", null));
  }

  public static <T> ResponseEntity<ApiResponse<T>> success(T data) {
    return ResponseEntity.ok(new ApiResponse<>("success", data));
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(
      HttpStatus status, String message, T data) {
    return ResponseEntity.status(status).body(new ApiResponse<>("error", message, data, true));
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message) {
    return ResponseEntity.status(status).body(new ApiResponse<>("error", message, null, true));
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatusCode status, String message) {
    return ResponseEntity.status(status).body(new ApiResponse<>("error", message, null, true));
  }

  public static <T> ResponseEntity<ApiResponse<T>> error(String message) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
}
