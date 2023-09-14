package com.woowahanbank.backend.global.notification.service;


import com.woowahanbank.backend.global.notification.domain.FCMToken;
import com.woowahanbank.backend.global.notification.repository.FCMTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FCMTokenService {

    private final FCMTokenRepository fcmTokenRepository;

    public void saveToken(String nickname, String token) {
        FCMToken fcmToken = new FCMToken();
        fcmToken.setNickname(nickname);
        fcmToken.setToken(token);
        fcmTokenRepository.save(fcmToken);
    }

    public String getToken(String nickname) {
        Optional<FCMToken> fcmToken = fcmTokenRepository.findById(nickname);
        return fcmToken.map(FCMToken::getToken).orElse(null);
    }
}
