package com.example.demo.dto;

public class Target {
  private String type;
  private String name;

  public Target(String type, String name) {
    this.type = type;
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }
}
