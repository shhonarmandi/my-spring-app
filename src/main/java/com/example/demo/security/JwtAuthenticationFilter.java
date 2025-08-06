package com.example.demo.security;

import com.example.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    var authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) return;

    var token = authHeader.substring(7);

    try {
      var userEmail = JwtUtil.validateAndGetSubject(token);
      //      var authentication = new UsernamePasswordAuthenticationToken(userEmail, null,
      // List.of());
      //      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception e) {
      // Invalid token - you may want to reject or just continue
    }

    filterChain.doFilter(request, response);
  }
}
