package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Errors {
  private String code;
  private String message;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Target target;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String moreInfo;

  public Errors(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public Errors(String code, String message, Target target) {
    this.code = code;
    this.message = message;
    this.target = target;
  }

  public Errors(String code, String message, Target target, String moreInfo) {
    this.code = code;
    this.message = message;
    this.target = target;
    this.moreInfo = moreInfo;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public Target getTarget() {
    return target;
  }

  public String getMoreInfo() {
    return moreInfo;
  }
}
