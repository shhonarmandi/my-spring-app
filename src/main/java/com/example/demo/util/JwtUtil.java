package com.example.demo.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtil {
  private static final String SECRET = "my-very-strong-secret-key-for-hs512";
  private static final SecretKey hmacKey =
      Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
  private static final long EXPIRATION_TIME = 86400000; // 1 day in ms

  public static String generateToken(String subject) {
    return Jwts.builder()
        .subject(subject)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(hmacKey)
        .compact();
  }

  public static String validateAndGetSubject(String token) {
    return Jwts.parser()
        .verifyWith(hmacKey)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }
}
