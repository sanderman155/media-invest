package com.kinzhitaev.mibackend.security.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.kinzhitaev.mibackend.security.details.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpiration}")
    private long jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return JWT.create().
                withSubject(userPrincipal.getUsername()).
                withIssuedAt(new Date()).
                withExpiresAt(new Date((new Date()).getTime() + jwtExpiration)).
                sign(Algorithm.HMAC512(jwtSecret));
    }

    public boolean validateJwtToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(jwtSecret)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
            return JWT.decode(token).getSubject();
    }
}
