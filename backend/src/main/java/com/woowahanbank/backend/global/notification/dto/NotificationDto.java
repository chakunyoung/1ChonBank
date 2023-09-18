package com.woowahanbank.backend.global.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private String title;
    private String body;
    private String icon;
    private String clickAction;
    private String nickname;

    public FCMMessage.Notification toNotification() {
        return FCMMessage.Notification.builder()
                .title(this.title)
                .body(this.body)
                .icon(this.icon)
                .clickAction(this.clickAction)
                .build();
    }
}
