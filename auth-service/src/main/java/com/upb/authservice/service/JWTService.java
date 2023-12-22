package com.upb.authservice.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    public String extractUsername(String token);
    public boolean isTokenValid(String token, UserDetails userDetails);

    Claims extractAllClaims(String token);

    public String generateToken(UserDetails userDetails);

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
