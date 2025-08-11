package com.example.demo.controller.v1;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.v1.Auth.AuthRequest;
import com.example.demo.dto.v1.Auth.AuthResponse;
import com.example.demo.service.v1.AuthService;
import com.example.demo.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest request) {
    var response = authService.login(request);
    return ResponseUtil.success(response);
  }
}
