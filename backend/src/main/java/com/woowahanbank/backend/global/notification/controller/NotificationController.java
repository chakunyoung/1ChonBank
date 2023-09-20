package com.woowahanbank.backend.global.notification.controller;

import com.woowahanbank.backend.global.notification.dto.FCMTokenRequestDto;
import com.woowahanbank.backend.global.notification.service.FCMTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

	private final FCMTokenService fcmTokenService;

    @PostMapping("/token")
    public ResponseEntity<?> saveToken(@RequestBody FCMTokenRequestDto tokenRequestDto) {
        log.info("token = {}", tokenRequestDto.getToken());
        log.info("nickname = {}", tokenRequestDto.getNickname());
        fcmTokenService.saveToken(tokenRequestDto.getNickname(), tokenRequestDto.getToken());
        return ResponseEntity.ok().build();
    }
}
