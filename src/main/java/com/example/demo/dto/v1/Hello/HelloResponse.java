package com.example.demo.dto.v1.Hello;

public class HelloResponse {
  private String message;

  public HelloResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
