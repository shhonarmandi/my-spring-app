package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.Instant;

@Configuration
public class SecurityConfig {

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
    return new JwtAuthenticationFilter(jwtUtil);
  }

  @Bean
  public SecurityFilterChain filterChain(
      HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

    http
        // Disable CSRF (only for stateless APIs)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Detect if endpoint is public or protected
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/v1/login", "/v1/refresh")
                    .permitAll()
                    .anyRequest()
                    .authenticated())

        // Detect token in header and set Spring SecurityContextHolder
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

        // Root level security filters exception handlers - runs before MvcExceptionHandler
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                          res.setContentType("application/json");
                          res.getWriter().write(generateErrorResponse("unauthorized"));
                        })
                    .accessDeniedHandler(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                          res.setContentType("application/json");
                          res.getWriter().write(generateErrorResponse("forbidden"));
                        }))

        // Other configuration to stay with stateless approach
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable);

    return http.build();
  }

  private String generateErrorResponse(String message) {
    return "{\"status\":\"error\",\"message\":\""
        + message
        + "\",\"timestamp\":\""
        + Instant.now().toString()
        + "\"}";
  }
}
