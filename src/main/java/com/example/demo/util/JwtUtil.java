package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private static final String SECRET =
      "THIS_IS_NOT_A_REAL_JWT_SECRET_KEY_SO_DONT_USE_IT_IN_PRODUCTION!!!MY_VERY_STRONG_SECRET_KEY";
  private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
  private final Duration accessTokenTtl = Duration.ofMinutes(15);
  private final Duration refreshTokenTtl = Duration.ofDays(30);

  public String validateAndGetSubject(String token) {
    return getTokenPayload(token).getSubject();
  }

  public boolean isRefreshToken(String token) {
    var tokenType = getTokenPayload(token).get("token_type", String.class);
    return "refresh".equals(tokenType);
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

  private Claims getTokenPayload(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
  }
}
