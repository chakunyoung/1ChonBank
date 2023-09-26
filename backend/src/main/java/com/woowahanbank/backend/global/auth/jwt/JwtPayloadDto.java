package com.woowahanbank.backend.global.auth.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtPayloadDto {
	private String aud;
	private String sub;
	@JsonProperty("auth_time")
	private long authTime;
	private String iss;
	private String nickname;
	private long exp;
	private long iat;
	private String picture;
	private String email;
	// 구글용 추가
	private String azp;
	private String at_hash;
	private String name;
	@JsonProperty("given_name")
	private String givenName;
	@JsonProperty("family_name")
	private String familyName;
	private String locale;
}
