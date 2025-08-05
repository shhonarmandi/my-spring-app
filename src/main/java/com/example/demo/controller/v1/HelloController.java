package com.example.demo.controller.v1;

import com.example.demo.dto.v1.Hello.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloController {

  @GetMapping("/v1/hello")
  public HelloResponse hello(@RequestParam(required = false) String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("name is required");
    }

    return new HelloResponse("Hello, " + name + " \uD83D\uDC4B!");
  }
}
