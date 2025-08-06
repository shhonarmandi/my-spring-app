package com.example.demo.controller.v1;

import com.example.demo.dto.v1.Hello.HelloResponse;
import com.example.demo.dto.v1.User.HelloRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
class HelloController {

  @GetMapping("/hello")
  public HelloResponse hello(@Valid @ModelAttribute HelloRequest request) {
    return new HelloResponse("Hello, " + request.getName() + " \uD83D\uDC4B!");
  }
}
