package com.woowahanbank.backend.global.notification.domain;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RequiredArgsConstructor
@Data
@RedisHash(value = "nickname", timeToLive = 1209600)
public class FCMToken {
    @Id
    private String nickname;
    private String token;
}
