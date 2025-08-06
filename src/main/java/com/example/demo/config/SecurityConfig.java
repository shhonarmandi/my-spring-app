package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // Disable CSRF (only for stateless APIs)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        // Detect if endpoint is public or protected
        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/v1/login").permitAll().anyRequest().authenticated())

        // Detect token in header and set Spring SecurityContextHolder
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

        // Root level security filters exception handlers - runs before MvcExceptionHandler
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                          res.setContentType("application/json");
                          res.getWriter().write("{\"status\":401,\"message\":\"unauthorized\"}");
                        })
                    .accessDeniedHandler(
                        (req, res, e) -> {
                          res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                          res.setContentType("application/json");
                          res.getWriter().write("{\"status\":403,\"message\":\"forbidden\"}");
                        }))

        // Other configuration to stay with stateless approach
        .httpBasic(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable);

    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }
}
