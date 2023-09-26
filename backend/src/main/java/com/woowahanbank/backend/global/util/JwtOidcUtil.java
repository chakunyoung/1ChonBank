package com.woowahanbank.backend.global.util;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowahanbank.backend.global.auth.jwt.JwtHeaderDto;
import com.woowahanbank.backend.global.auth.jwt.JwtPayloadDto;
import com.woowahanbank.backend.global.auth.oauth.oidc.publickey.PublicKeyService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtOidcUtil {
	private static final String ISS = "https://kauth.kakao.com";
	private final String aud;
	private final PublicKeyService publicKeyService;

	@Autowired // 생성자를 통한 주입을 위해 @Autowired 어노테이션 추가
	public JwtOidcUtil(@Value("${spring.security.oauth2.client.registration.kakao.client-id}") String aud,
		PublicKeyService publicKeyService) {
		this.aud = aud;
		this.publicKeyService = publicKeyService;
	}

	public JwtPayloadDto decodeIdToken(String idToken) throws JsonProcessingException {
		idToken = idToken.replace(JwtTokenUtil.TOKEN_PREFIX, "");
		String jwtToken = idToken;

		// 토큰을 . 기준으로 분리하여 헤더, 페이로드, 서명 부분으로 나눕니다.
		String[] jwtParts = jwtToken.split("\\.");

		// 헤더와 페이로드를 디코딩합니다.
		String headerJson = new String(Base64.getUrlDecoder().decode(jwtParts[0]));
		String payloadJson = new String(Base64.getUrlDecoder().decode(jwtParts[1]));

		ObjectMapper objectMapper = new ObjectMapper();
		JwtHeaderDto jwtHeaderDto = objectMapper.readValue(headerJson, JwtHeaderDto.class);
		JwtPayloadDto jwtPayloadDto = objectMapper.readValue(payloadJson, JwtPayloadDto.class);

		//라이브러리에 토큰 검증시에 만료시간 검증이 없어서 미리하기
		if (jwtPayloadDto.getExp() < System.currentTimeMillis() / 1000) {
			throw new JWTVerificationException("토큰이 만료되었습니다.");
		}

		// 검증에 필요한 시크릿 키나 공개키를 생성합니다.
		PublicKey publicKey = publicKeyService.getPublicKeyByKid(jwtHeaderDto.getKid());

		// JWTVerifier를 생성하고 토큰을 검증합니다.
		JWTVerifier verifier = JWT
			.require(Algorithm.RSA256((RSAPublicKey)publicKey, null))
			.ignoreIssuedAt()
			.withIssuer(ISS)
			.withAudience(aud)
			.build();

		DecodedJWT decodedJWT = verifier.verify(idToken);
		return jwtPayloadDto;

		// 검증된 토큰의 내용을 출력합니다.
		// 필요한 정보들을 디코딩된 JWT로부터 가져와서 사용할 수 있습니다.

		// 필요한 검증 내용을 추가로 확인하고 처리할 수 있습니다.
		// 예를 들면, 만료 시간 확인 등의 검증을 수행할 수 있습니다.
	}
}
