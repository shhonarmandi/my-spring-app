package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

public class ApiResponse<T> {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final String timestamp;

  private String status;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  public ApiResponse(String status, T data) {
    this.status = status;
    this.message = null;
    this.data = data;
    this.timestamp = null;
  }

  public ApiResponse(String status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
    this.timestamp = null;
  }

  public ApiResponse(String status, String message, T data, boolean includeTimestamp) {
    this.status = status;
    this.message = message;
    this.data = data;
    this.timestamp = includeTimestamp ? Instant.now().toString() : null;
  }

  public String getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
