package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // Disable CSRF (only for stateless APIs)
        .csrf(AbstractHttpConfigurer::disable)

        // Authorize requests
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/v1/login", "/v1/register")
                    .permitAll()
                    .anyRequest()
                    .authenticated())

        // Other security configurations (optional)
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  //  @Bean
  //  public JwtAuthenticationFilter jwtAuthenticationFilter() {
  //    return new JwtAuthenticationFilter();
  //  }
}
