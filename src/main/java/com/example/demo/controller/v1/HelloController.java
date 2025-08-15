package com.example.demo.controller.v1;

import com.example.demo.dto.v1.User.HelloRequest;
import com.example.demo.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
class HelloController {

  @GetMapping("/hello")
  public ResponseEntity<Void> hello(@Valid @ModelAttribute HelloRequest request) {
    return ResponseUtil.success();
  }
}
