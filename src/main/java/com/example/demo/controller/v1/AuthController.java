package com.example.demo.controller.v1;

import com.example.demo.dto.v1.Auth.AuthResponse;
import com.example.demo.dto.v1.Auth.LoginRequest;
import com.example.demo.service.v1.AuthService;
import com.example.demo.util.ResponseUtil;
import jakarta.validation.Valid;
import java.time.Duration;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
    var response = authService.login(request);

    var refreshCookie =
        ResponseCookie.from("refresh_token", response.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .path("/v1/refresh")
            .maxAge(Duration.ofDays(30))
            .build();

    return ResponseUtil.success(response, refreshCookie);
  }

  // TODO: what if cookie is not in the header
  @PostMapping("/refresh")
  public ResponseEntity<AuthResponse> refresh(
      @CookieValue(name = "refresh_token") String refreshToken) {
    var response = authService.rotate(refreshToken);

    var refreshCookie =
        ResponseCookie.from("refresh_token", response.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .sameSite("Strict")
            .path("/v1/refresh")
            .maxAge(Duration.ofDays(30))
            .build();

    return ResponseUtil.success(response, refreshCookie);
  }
}
