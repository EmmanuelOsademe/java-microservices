package com.emmydev.clients.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotBlank(message = "Please provide recipient Id")
    private Long toCustomerId;

    @NotBlank(message = "Please provide customer's Email")
    private String toCustomerEmail;

    @NotBlank(message = "Please provide message")
    private String message;

    @NotBlank(message = "Please provide email subject")
    private String subject;
}
