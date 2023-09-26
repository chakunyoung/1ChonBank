package com.woowahanbank.backend.global.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

/**
 * jwt 토큰 유틸 정의.
 */
@Component
@Slf4j
public class JwtTokenUtil {
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String ISSUER = "ilchonbank.com";

	public static final long TWO_DAYS = 172_800;
	private static String secretKey;
	private static long accessExpirationTime;
	private static long refreshExpirationTime;

	@Autowired
	public JwtTokenUtil(@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.access-expiration}") Integer accessExpirationTime,
		@Value("${jwt.refresh-expiration}") Integer refreshExpirationTime) {
		JwtTokenUtil.secretKey = secretKey;
		JwtTokenUtil.accessExpirationTime = accessExpirationTime;
		JwtTokenUtil.refreshExpirationTime = refreshExpirationTime;
	}

	public static JWTVerifier getVerifier() {
		return JWT
			.require(Algorithm.HMAC512(secretKey.getBytes()))
			.withIssuer(ISSUER)
			.build();
	}

	public static JWTVerifier getVerifier(String token) {
		return JWT
			.require(Algorithm.HMAC512(secretKey.getBytes()))
			.withIssuer(ISSUER)
			.build();
	}

	public static String getAccessToken(String userId) {
		Date expires = JwtTokenUtil.getTokenExpiration(accessExpirationTime);
		return JWT.create()
			.withSubject(userId)
			.withExpiresAt(expires)
			.withIssuer(ISSUER)
			.withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
			.sign(Algorithm.HMAC512(secretKey.getBytes()));
	}

	public static String getRefreshToken(String userId) {
		Date expires = JwtTokenUtil.getTokenExpiration(refreshExpirationTime);
		return JWT.create()
			.withSubject(userId)
			.withExpiresAt(expires)
			.withIssuer(ISSUER)
			.withIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
			.sign(Algorithm.HMAC512(secretKey.getBytes()));
	}

	public static Date getTokenExpiration(long expirationTime) {
		Date now = new Date();
		return new Date(now.getTime() + expirationTime);
	}

	public static DecodedJWT handleError(String token) {
		JWTVerifier verifier = JWT
			.require(Algorithm.HMAC512(secretKey.getBytes()))
			.withIssuer(ISSUER)
			.build();

		try {
			return verifier.verify(token.replace(TOKEN_PREFIX, ""));
		} catch (AlgorithmMismatchException ex) {
			throw ex;
		} catch (InvalidClaimException ex) {
			throw ex;
		} catch (SignatureGenerationException ex) {
			throw ex;
		} catch (SignatureVerificationException ex) {
			throw ex;
		} catch (TokenExpiredException ex) {
			throw ex;
		} catch (JWTCreationException ex) {
			throw ex;
		} catch (JWTDecodeException ex) {
			throw ex;
		} catch (JWTVerificationException ex) {
			throw ex;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
