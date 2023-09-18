package com.woowahanbank.backend.global.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woowahanbank.backend.global.notification.dto.FCMTokenRequestDto;
import com.woowahanbank.backend.global.notification.service.FCMTokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

	private final FCMTokenService fcmTokenService;

	@PostMapping("/token")
	public ResponseEntity<?> saveToken(@RequestBody FCMTokenRequestDto tokenRequestDto) {
		System.out.println(tokenRequestDto.getToken());
		System.out.println(tokenRequestDto.getNickname());
		fcmTokenService.saveToken(tokenRequestDto.getNickname(), tokenRequestDto.getToken());
		return ResponseEntity.ok().build();
	}
}
