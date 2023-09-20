package com.woowahanbank.backend.global.notification.dto;

import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto implements Serializable{
    private String title;
    private String body;

    public Notification toNotification() {
        return new Notification(this.body, this.title);
    }
}