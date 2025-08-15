package com.example.demo.dto.v1.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HelloRequest {
  @NotBlank(message = "Name is required.")
  @Size(max = 50, message = "Name must be at most 50 characters.")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
