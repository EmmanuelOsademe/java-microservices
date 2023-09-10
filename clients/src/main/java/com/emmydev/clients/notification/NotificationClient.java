package com.emmydev.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(
        value = "notification",
        path = "/api/v1/notification"
)
public interface NotificationClient {

    @PostMapping
    String sendNotification(@Valid @RequestBody NotificationRequest request);
}
