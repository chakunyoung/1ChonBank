package com.woowahanbank.backend.global.notification.listener;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.woowahanbank.backend.global.notification.dto.FCMMessage;
import com.woowahanbank.backend.global.notification.dto.NotificationDto;
import com.woowahanbank.backend.global.notification.service.FCMTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationQueueReceiver {
    private final FCMTokenService fcmTokenService;

    @RabbitListener(queues = "${spring.rabbitmq.queue-name}")
    public void receiveMessage(NotificationDto notificationDto) {
        // FCM 메시지 구성
        FCMMessage fcmMessage = FCMMessage.builder()
                .validate_only(false)
                .message(FCMMessage.Message.builder()
                        .notification(notificationDto.toNotification())
                        .token(fcmTokenService.getToken(notificationDto.getNickname()))/* 여기에 Firebase Token 입력 */
                        .build())
                .build();

        // FCM 서비스로 메시지 전송 로직
        sendFCMMessage(fcmMessage);
    }

    private void sendFCMMessage(FCMMessage fcmMessage) {
        // FCM 전송 로직. Firebase SDK를 사용해 FCM 서버로 요청 전송
        Message message = Message.builder()
                .putData("title", fcmMessage.getMessage().getNotification().getTitle())
                .putData("body", fcmMessage.getMessage().getNotification().getBody())
                .putData("icon", fcmMessage.getMessage().getNotification().getIcon())
                .putData("clickAction", fcmMessage.getMessage().getNotification().getClickAction())
                .setToken(fcmMessage.getMessage().getToken())
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}

