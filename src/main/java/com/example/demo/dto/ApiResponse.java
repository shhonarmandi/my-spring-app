package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;

public class ApiResponse<T> {
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private final String timestamp;

  private int status;
  private boolean success;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private T data;

  public ApiResponse(int status, boolean success, String message, T data) {
    this.status = status;
    this.success = success;
    this.message = message;
    this.data = data;
    this.timestamp = null;
  }

  public ApiResponse(
      int status, boolean success, String message, T data, boolean includeTimestamp) {
    this.status = status;
    this.success = success;
    this.message = message;
    this.data = data;
    this.timestamp = includeTimestamp ? Instant.now().toString() : null;
  }

  public int getStatus() {
    return status;
  }

  public boolean isSuccess() {
    return success;
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
