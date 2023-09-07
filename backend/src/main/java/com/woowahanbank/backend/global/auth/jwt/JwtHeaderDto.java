package com.woowahanbank.backend.global.auth.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtHeaderDto {
	private String kid;
	private String typ;
	private String alg;
}
