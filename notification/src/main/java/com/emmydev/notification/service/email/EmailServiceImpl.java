package com.emmydev.notification.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record EmailServiceImpl(JavaMailSender mailSender) implements EmailService {
    @Override
    public void sendSimpleMessage(String from, String to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setSubject(text);

            mailSender.send(message);
        }catch (Exception ex){
            log.info(text);
            throw new RuntimeException();
        }
    }
}
