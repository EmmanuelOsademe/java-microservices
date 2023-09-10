package com.emmydev.notification.service.email;

public interface EmailService {

    void sendSimpleMessage(String from, String to, String subject, String text);
}
