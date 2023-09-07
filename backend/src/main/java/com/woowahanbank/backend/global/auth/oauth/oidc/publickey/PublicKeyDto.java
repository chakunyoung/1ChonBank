package com.woowahanbank.backend.global.auth.oauth.oidc.publickey;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@RedisHash("publicKey")
public class PublicKeyDto {
	@Id
	public String kid;
	public String kty;
	public String alg;
	public String use;
	public String n;
	public String e;

	public PublicKey toPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		// RSA 알고리즘을 이용하여 KeyFactory 생성
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		// n과 e 값을 URL Safe Base64 디코딩
		byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
		byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

		// RSA PublicKeySpec 생성
		RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(
			new BigInteger(1, modulusBytes),
			new BigInteger(1, exponentBytes)
		);

		// PublicKey 생성
		return keyFactory.generatePublic(rsaPublicKeySpec);
	}
}
