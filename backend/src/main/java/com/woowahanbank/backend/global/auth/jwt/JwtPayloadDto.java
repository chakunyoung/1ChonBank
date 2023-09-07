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
}
