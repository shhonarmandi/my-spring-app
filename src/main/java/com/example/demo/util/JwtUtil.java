package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.example.demo.exception.TokenValidationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private final String SECRET =
      "THIS_IS_NOT_A_REAL_JWT_SECRET_KEY_SO_DONT_USE_IT_IN_PRODUCTION!!!MY_VERY_STRONG_SECRET_KEY";
  private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
  private final Duration accessTokenTtl = Duration.ofMinutes(15);
  private final Duration refreshTokenTtl = Duration.ofDays(30);

  public String getRefreshTokenSubject(String token) throws TokenValidationException {
    try {
      var parsedToken = parseSignedClaims(token);
      var tokenType = parsedToken.get("token_type", String.class);
      if (tokenType.equals("refresh")) {
        return parsedToken.getSubject();
      } else {
        throw new TokenValidationException();
      }
    } catch (Exception e) {
      throw new TokenValidationException();
    }
  }

  public String getAccessTokenSubject(String token) throws TokenValidationException {
    try {
      var parsedToken = parseSignedClaims(token);
      var tokenType = parsedToken.get("token_type", String.class);
      if (tokenType.equals("access")) {
        return parsedToken.getSubject();
      } else {
        throw new TokenValidationException();
      }
    } catch (Exception e) {
      throw new TokenValidationException();
    }
  }

  public String generateAccessToken(String subject) {
    var now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(accessTokenTtl)))
        .claim("token_type", "access")
        .signWith(key)
        .compact();
  }

  public String generateRefreshToken(String subject) {
    var now = Instant.now();
    return Jwts.builder()
        .subject(subject)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(refreshTokenTtl)))
        .claim("token_type", "refresh")
        .signWith(key)
        .compact();
  }

  private Claims parseSignedClaims(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
