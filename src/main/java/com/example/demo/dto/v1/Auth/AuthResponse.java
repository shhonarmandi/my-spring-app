package com.example.demo.dto.v1.Auth;

public class AuthResponse {
  private String message;

  public AuthResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
