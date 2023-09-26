package com.woowahanbank.backend.global.notification.event;

import com.woowahanbank.backend.global.notification.dto.NotificationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.io.Serializable;


@Getter
public class NotificationEvent extends ApplicationEvent implements Serializable {
    private NotificationDto notificationDto;
    private String clickAction;
    private String nickname;

    public NotificationEvent(Object source, String nickname, String clickAction, NotificationDto notificationDto) {
        super(source);
        this.notificationDto = notificationDto;
        this.clickAction = clickAction;
        this.nickname = nickname;
    }
}
