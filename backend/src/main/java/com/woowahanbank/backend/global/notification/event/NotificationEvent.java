package com.woowahanbank.backend.global.notification.event;

import com.woowahanbank.backend.global.notification.dto.NotificationDto;
import org.springframework.context.ApplicationEvent;


public class NotificationEvent extends ApplicationEvent {
    private NotificationDto data;
    private String nickname;

    public NotificationEvent(Object source, String nickname, NotificationDto notificationDto) {
        super(source);
        this.nickname = nickname;
        this.data = notificationDto;
    }

    public String getNickname() {
        return nickname;
    }

    public NotificationDto getData() {
        return data;
    }
}
