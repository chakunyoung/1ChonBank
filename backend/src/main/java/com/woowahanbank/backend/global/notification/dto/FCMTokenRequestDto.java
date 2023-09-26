package com.woowahanbank.backend.global.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FCMTokenRequestDto {
    private String nickname;
    private String token;
}
