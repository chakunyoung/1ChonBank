package com.woowahanbank.backend.global.notification.dto;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class FCMMessage {
    private boolean validate_only;
    private Message message;

    @Builder
    @AllArgsConstructor
    @Data
    public static class Message {
        private Notification notification;
        private String icon;
        private String clickAction;
        private String token;
    }
}
