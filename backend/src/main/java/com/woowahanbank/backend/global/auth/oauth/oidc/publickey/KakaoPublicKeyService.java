package com.woowahanbank.backend.global.auth.oauth.oidc.publickey;

import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoPublicKeyService implements PublicKeyService {
	private static final String KAKAO_OPEN_KEY_URL = "https://kauth.kakao.com/.well-known/jwks.json";

	private final PublicKeyRepository publicKeyRepository;

	@Override
	public List<PublicKeyDto> fetchPublicKeysFromKakao() {
		RestTemplate rt = new RestTemplate();

		ResponseEntity<String> response = rt.exchange(
			KAKAO_OPEN_KEY_URL,
			HttpMethod.GET,
			null,
			String.class
		);

		if (response.getStatusCode().is2xxSuccessful()) {
			String jsonResponse = response.getBody();
			ObjectMapper mapper = new ObjectMapper();

			try {
				JsonNode root = mapper.readTree(jsonResponse);
				JsonNode keysNode = root.get("keys");

				List<PublicKeyDto> publicKeys = mapper.readValue(
					keysNode.toString(),
					new TypeReference<List<PublicKeyDto>>() {
					}
				);
				return publicKeys;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("HTTP GET 요청 실패 - 응답 코드: " + response.getStatusCodeValue());
		}
		return null;
	}

	@Override
	public void cachePublicKeysInRedis(List<PublicKeyDto> publicKeyDtos) {
		publicKeyDtos.forEach(publicKey -> publicKeyRepository.save(publicKey));
	}

	@Override
	public PublicKey getPublicKeyByKid(String kid) {
		Optional<PublicKeyDto> cachedPublicKeyById = publicKeyRepository.findById(kid);
		log.info("cached key = {}", cachedPublicKeyById);
		cachedPublicKeyById = fetchAndUpdateIfNotFound(kid, cachedPublicKeyById);

		if (!cachedPublicKeyById.isPresent()) {
			throw new RuntimeException("해당하는 공개키가 없습니다.");
		}

		PublicKey publicKey = null;
		try {
			publicKey = publicKeyDtoToPublicKey(cachedPublicKeyById.get());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		return publicKey;
	}

	public Optional<PublicKeyDto> fetchAndUpdateIfNotFound(String kid, Optional<PublicKeyDto> cachedPublicKeyById) {
		if (!cachedPublicKeyById.isPresent()) {
			List<PublicKeyDto> publicKeys = fetchPublicKeysFromKakao();
			cachePublicKeysInRedis(publicKeys);

			cachedPublicKeyById = publicKeyRepository.findById(kid);
		}
		return cachedPublicKeyById;
	}

	public PublicKey publicKeyDtoToPublicKey(PublicKeyDto publicKeyDto) throws
		NoSuchAlgorithmException,
		InvalidKeySpecException {
		return publicKeyDto.toPublicKey();
	}
}
