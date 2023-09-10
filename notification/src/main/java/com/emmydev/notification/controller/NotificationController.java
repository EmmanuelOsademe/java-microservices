package com.emmydev.notification.controller;

import com.emmydev.clients.notification.NotificationRequest;
import com.emmydev.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/notification")
public record NotificationController(NotificationService notificationService) {

    @PostMapping
    public String sendNotification(@RequestBody NotificationRequest request){
        log.info(request.toString());
        String response = notificationService.sendNotification(request);
        log.info("Email notification sent");
        return response;
    }
}
