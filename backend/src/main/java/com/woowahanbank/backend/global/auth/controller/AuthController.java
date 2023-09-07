package com.woowahanbank.backend.global.auth.controller;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.LoginDto;
import com.woowahanbank.backend.domain.user.service.UserService;
import com.woowahanbank.backend.global.response.BaseResponse;
import com.woowahanbank.backend.global.util.JwtTokenUtil;
import com.woowahanbank.backend.global.util.OidcUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final RedisTemplate<String, String> template;
	private final OidcUtil oidcUtil;

	@GetMapping("/api/users/login/kakao/code")
	public ResponseEntity<?> getOauthCode(@RequestParam String code) {
		log.info("kakao code = {}", code);
		Map<String, String> body = new HashMap<>();
		body.put("code", code);
		return BaseResponse.okWithData(HttpStatus.OK, "retrun code", body);
	}

	@GetMapping("test")
	public ResponseEntity<?> test(@RequestParam String code) throws JsonProcessingException {
		oidcUtil.decodeIdToken(code);
		return BaseResponse.ok(HttpStatus.OK, "ok");
	}

	@PostMapping("/api/users/login")
	public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto) {
		String userId = loginDto.getUserId();
		String password = loginDto.getPassword();
		log.info("login start with id = {}, pw = {}", userId, password);

		Optional<User> optionalMember = userService.findByUserId(userId);
		User user = null;
		// if (optionalMember.isPresent() && !optionalMember.get().getDelete()) {
		// 	user = optionalMember.get();
		// } else {
		// 	return BaseResponse.fail("login fail", 401);
		// }

		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if (passwordEncoder.matches(password, user.getPassword())) {
			log.info("login ok!");
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			Map<String, String> tokens = new LinkedHashMap<>();
			tokens.put("access-token", JwtTokenUtil.getAccessToken(userId));
			tokens.put("refresh-token", JwtTokenUtil.getRefreshToken(userId));

			//Redis에 20일 동안 저장
			template.opsForValue().set("refresh " + userId, tokens.get("refresh-token"), Duration.ofDays(20));

			return BaseResponse.okWithData(HttpStatus.OK, "login Success", tokens);
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return BaseResponse.fail("login fail", 401);
	}

	@PostMapping("/api/users/reissue")
	public ResponseEntity<?> reissueAccessToken(@RequestHeader("Authorization") String token) {
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

		String dbToken = template.opsForValue().get("refresh " + userId);

		String reissuedRefreshToken = null;
		String reissuedAccessToken = JwtTokenUtil.getAccessToken(userId);

		if (dbToken.equals(token.replace(JwtTokenUtil.TOKEN_PREFIX, ""))) {

			//refresh token 만료가 2일 이내라면 재발급
			if (expiresAt.getTime() - new Date().getTime() < JwtTokenUtil.TWO_DAYS) {
				reissuedRefreshToken = JwtTokenUtil.getRefreshToken(userId);
				template.opsForValue().set
					(
						"refresh " + userId,
						reissuedRefreshToken,
						Duration.ofDays(20)
					);
			}
			body.put("access-token", reissuedAccessToken);
			body.put("refresh-token", reissuedRefreshToken);

			return BaseResponse.okWithData(HttpStatus.OK, "Token Reissuance Successful", body);
		}
		return BaseResponse.fail("Expired token, login again!", 419);
	}
}
