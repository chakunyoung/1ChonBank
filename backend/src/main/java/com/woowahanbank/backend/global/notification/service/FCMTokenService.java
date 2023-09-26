package com.woowahanbank.backend.global.notification.service;


import com.woowahanbank.backend.global.notification.domain.FCMToken;
import com.woowahanbank.backend.global.notification.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMTokenService {

    private final FCMTokenRepository fcmTokenRepository;

    public void saveToken(String nickname, String token) {
        FCMToken fcmToken = new FCMToken();
        fcmToken.setNickname(nickname);
        fcmToken.setToken(token);
        fcmTokenRepository.save(fcmToken);
    }

    public String getToken(String nickname) {
        FCMToken fcmToken = fcmTokenRepository.findById(nickname)
                .orElseThrow(() -> new IllegalArgumentException("FCM Token을 찾을 수 없습니다"));

        return fcmToken.getToken();
    }
}
