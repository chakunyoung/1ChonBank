package com.woowahanbank.backend.global.auth.oauth.oidc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IdToken {
	private String iss;
	private String aud;
	private String sub;
	private String iat;
	private String exp;
}
