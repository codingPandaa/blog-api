package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogAPIException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	// refer application.properties for keys to add in @Value
	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	// generate JWT token
	public String generateToken(Authentication authentication) {

		String username = ((UserDetails) authentication.getPrincipal()).getUsername();
//		String username = authentication.getName();

		Date currentDate = new Date();

		Date expiredDate = new Date(currentDate.getTime() + jwtExpirationDate);

		// generating token by setting values
		String token = Jwts.builder().subject(username).issuedAt(currentDate).expiration(expiredDate).signWith(key())
				.compact();
		return token;
	}

	// getting key
	// decoding key present in application.properties
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	// get username from JWT token
	public String getUserName(String token) {

		return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();

	}

	// validate JWT token
	public boolean validateToken(String token) {

		try {

			Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
			return true;

		} catch (MalformedJwtException malformedJwtException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt token");

		} catch (ExpiredJwtException expiredJwtException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired Jwt token");

		} catch (UnsupportedJwtException unsupportedJwtException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupprted Jwt token");

		} catch (IllegalArgumentException illegalArgumentException) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is null or empty");

		}
	}

}
