package com.woowahanbank.backend.global.auth.oauth.oidc.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 사용자 인증 정보
 * iss: ID 토큰을 발급한 인증 기관 정보, https://kauth.kakao.com로 고정
 * aud: ID 토큰이 발급된 앱의 앱 키
 * sub: ID 토큰에 해당하는 사용자의 회원번호
 * iat: ID 토큰 발급 또는 갱신 시각
 * auth_time: 사용자가 카카오 로그인을 통해 인증을 완료한 시각
 * exp: 만료 시간
 * nonce: 카카오 로그인 요청 시 전달받은 임의의 문자열
 * nickname: 닉네임
 * picture: 프로필 사진
 * email: 이메일
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KakaoIdToken extends IdToken {

	@JsonProperty("auth_time")
	private String authTime;
	private String nickname;
	private String picture;
	private String email;
}
