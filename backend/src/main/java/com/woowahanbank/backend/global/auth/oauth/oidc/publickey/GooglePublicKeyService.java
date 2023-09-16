package com.woowahanbank.backend.global.auth.oauth.oidc.publickey;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class GooglePublicKeyService {
	private Map<String, RSAPublicKey> googlePublicKeys;

	public GooglePublicKeyService() throws Exception {
		URL jwkSetURL = new URL("https://www.googleapis.com/oauth2/v3/certs");
		JWKSet jwkSet = JWKSet.load(jwkSetURL);
		googlePublicKeys = jwkSet.getKeys().stream()
			.filter(jwk -> jwk instanceof RSAKey)
			.map(jwk -> (RSAKey) jwk)
			.collect(Collectors.toMap(RSAKey::getKeyID, rsaKey -> {
				try {
					return rsaKey.toRSAPublicKey();
				} catch (JOSEException e) {
					throw new RuntimeException("Error converting to RSAPublicKey", e);
				}
			}));
	}

	public RSAPublicKey getPublicKey(String kid) {
		return googlePublicKeys.get(kid);
	}
}
