package com.voron.jwtapp.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.voron.jwtapp.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	@Value("${jwt.token.secret}")
	private String SECRET_KEY;

	@Value("${jwt.token.expiration}")
	private long EXPIRATION_TIME;

	public String generateToken(String username, List<Role> roles) {
		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date now = new Date();
		System.out.println("\n===========>>>>>>>> IN JwtProvider.generateToken(), username: " + username);
		Date validity = new Date(now.getTime() + EXPIRATION_TIME);

		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", getRoleNames(roles));

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(signatureAlgorithm, secretKeyEncoder()).compact();
	}

	public List<String> getRoleNames(List<Role> roles) {
		return roles.stream().map(role -> role.getRoleTitle()).collect(Collectors.toList());
	}

	public Key secretKeyEncoder() {
		// We will sign our JWT with our ApiKey secret
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
		Key signKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
		return signKey;
	}

	public boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKeyEncoder()).parseClaimsJws(token);

			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}

			return true;
		} catch (JwtException | IllegalArgumentException e) {
			throw new JwtAuthenticationException("JWT token is expired or invalid");
		}
	}

	public String getUsernameFormToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(secretKeyEncoder()).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
}
