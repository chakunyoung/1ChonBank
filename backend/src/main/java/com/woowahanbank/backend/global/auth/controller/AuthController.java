package com.woowahanbank.backend.global.auth.controller;

import java.time.Duration;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.woowahanbank.backend.global.auth.security.CustomUserDetails;
import com.woowahanbank.backend.global.response.BaseResponse;
import com.woowahanbank.backend.global.util.JwtTokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final RedisTemplate<String, String> template;

	@PostMapping("/api/users/verify-token")
	public ResponseEntity<?> verifyToken(@AuthenticationPrincipal CustomUserDetails userDetails) {
		if (userDetails == null) {
			return BaseResponse.fail("DB에 해당되는 유작 없습니다.", 401);
		}
		return ResponseEntity.ok("JWT DB 검증 완료");
	}

	@PostMapping("/api/users/reissue")
	public ResponseEntity<?> reissueAccessToken(@RequestHeader("Authorization") String token) {
		System.out.println(token);
		DecodedJWT decodedJwt = null;
		final Map<String, Object> body = new LinkedHashMap<>();
		try {
			decodedJwt = JwtTokenUtil.handleError(token);
		} catch (Exception ex) {
			body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
			body.put("error", "Unauthorized");
			body.put("message", ex.getMessage());
			return ResponseEntity.status(498).body(body);
		}

		String userId = decodedJwt.getSubject();
		Date expiresAt = decodedJwt.getExpiresAt();

		String dbToken = template.opsForValue().get("refresh " + userId); // refresh token

		String reissuedAccessToken = JwtTokenUtil.getAccessToken(userId);

		if (dbToken.equals(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""))) {
			//refresh token 만료가 2일 이내라면 재발급
			if (expiresAt.getTime() - new Date().getTime() < JwtTokenUtil.TWO_DAYS) {
				dbToken = JwtTokenUtil.getRefreshToken(userId);
				template.opsForValue().set
					(
						"refresh " + userId,
						dbToken,
						Duration.ofDays(20)
					);
			}

			body.put("access-token", reissuedAccessToken);
			body.put("refresh-token", dbToken);

			return BaseResponse.okWithData(HttpStatus.OK, "Token Reissuance Successful", body);
		}
		return BaseResponse.fail("Expired token, login again!", 419);
	}
}
