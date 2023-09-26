package com.woowahanbank.backend.global.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.woowahanbank.backend.domain.user.domain.User;
import com.woowahanbank.backend.domain.user.dto.JoinDto;
import com.woowahanbank.backend.domain.user.repository.UserRepository;
import com.woowahanbank.backend.domain.user.service.UserService;
import com.woowahanbank.backend.global.auth.jwt.JwtPayloadDto;
import com.woowahanbank.backend.global.response.BaseResponse;
import com.woowahanbank.backend.global.util.JwtTokenUtil;
import com.woowahanbank.backend.global.util.OidcUtil;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class GoogleLoginController {

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.google.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
	private String redirectUri;

	private final WebClient webClient;
	private final OidcUtil oidcUtil;
	private final UserService userService;
	private final UserRepository userRepository;
	private final RedisTemplate<String, String> template;

	public GoogleLoginController(WebClient.Builder webClientBuilder, OidcUtil oidcUtil, UserService userService, UserRepository userRepository, RedisTemplate<String, String> template) {
		this.webClient = webClientBuilder.baseUrl("https://oauth2.googleapis.com/token").build();
		this.oidcUtil = oidcUtil;
		this.userService = userService;
		this.userRepository = userRepository;
		this.template = template;
	}

	@PostMapping("/api/auth/google")
	public ResponseEntity<?> googleLogin(@RequestBody Map<String, Object> data) {
		String code = (String) data.get("code");

		Map<String, Object> response = webClient.post()
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.body(BodyInserters
				.fromFormData("grant_type", "authorization_code")
				.with("client_id", clientId)
				.with("client_secret", clientSecret)
				.with("redirect_uri", redirectUri)
				.with("code", code))
			.retrieve()
			.bodyToMono(Map.class)
			.block();

		String idToken = (String) response.get("id_token");
		log.info("구글로 얻어온 id_token {}", idToken);

		JwtPayloadDto memberData;
		try {
			memberData = oidcUtil.decodeIdToken(idToken, "google");
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		Optional<User> user = userRepository.findByUserId(memberData.getSub());
		if (user.isEmpty()) {
			JoinDto newMember = JoinDto.builder()
				.userId(memberData.getSub())
				.score(500)
				.money(0)
				.build();
			userService.userRegister(newMember);
			user = userRepository.findByUserId(memberData.getSub());
		}

		String userId = user.get().getUserId();

		Map<String, String> tokens = new LinkedHashMap<>();
		tokens.put("access-token", JwtTokenUtil.getAccessToken(userId));
		tokens.put("refresh-token", JwtTokenUtil.getRefreshToken(userId));

		template.opsForValue().set("refresh " + userId, tokens.get("refresh-token"), Duration.ofDays(20));
		return BaseResponse.okWithData(HttpStatus.OK, "login Success", tokens);
	}
}
