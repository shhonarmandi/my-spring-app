package com.example.demo.dto.v1.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HelloRequest {
  @NotBlank(message = "FirstName is required.")
  @Size(max = 50, message = "FirstName must be at most 50 characters.")
  private String firstName;

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}
