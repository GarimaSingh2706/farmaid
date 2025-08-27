package com.examly.springapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Component
public class JwtUtils {

	@Value("${SECRET_KEY}")
	private String secretKey;

// Extract username from token
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

// Extract expiration date
	public Date extractExpiration(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
	}

// Validate token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

// Check if token is expired
	private boolean isTokenExpired(String token) {
		Date expiryDate = extractExpiration(token);
		return expiryDate.before(new Date());
	}

// Create token
	public String createToken(UserDetails userDetails) {

		return Jwts.builder().setSubject(userDetails.getUsername()) // usually the username
				.setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
}
