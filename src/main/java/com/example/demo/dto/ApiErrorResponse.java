package com.example.demo.dto;

import java.util.List;

public class ApiErrorResponse {
  private final List<Errors> errors;
  private final String trace;

  public ApiErrorResponse(List<Errors> errors, String trace) {
    this.errors = errors;
    this.trace = trace;
  }

  public List<Errors> getErrors() {
    return errors;
  }

  public String getTrace() {
    return trace;
  }
}
