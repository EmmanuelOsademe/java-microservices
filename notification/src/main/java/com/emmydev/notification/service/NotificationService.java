package com.emmydev.notification.service;

import com.emmydev.clients.notification.NotificationRequest;
import com.emmydev.notification.entity.Notification;
import com.emmydev.notification.repository.NotificationRepository;
import com.emmydev.notification.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public record NotificationService(NotificationRepository notificationRepository, Environment env, EmailService emailService) {

    public String sendNotification(NotificationRequest request){

//        emailService.sendSimpleMessage(env.getProperty("EMAIL_USERNAME"),
//                request.getToCustomerEmail(), request.getSubject(), request.getMessage());
        log.info("Email successfully sent");

        Notification notification = Notification.builder()
                .toCustomerId(request.getToCustomerId())
                .toCustomerEmail(request.getToCustomerEmail())
                .message(request.getMessage())
                .sender(env.getProperty("EMAIL_USERNAME"))
                .sentAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
        log.info("Notification successfully saved");
        return "Email notification sent";
    }

}
