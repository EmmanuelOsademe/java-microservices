package com.emmydev.customer.service;

import com.emmydev.clients.fraud.FraudCheckResponse;
import com.emmydev.clients.fraud.FraudClient;
import com.emmydev.clients.notification.NotificationClient;
import com.emmydev.clients.notification.NotificationRequest;
import com.emmydev.customer.database.CustomerRepository;
import com.emmydev.customer.entity.Customer;
import com.emmydev.customer.model.CustomerRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient) {

    public void registerCustomer(CustomerRegistrationRequest request){
        // todo: Check if email is not taken

        // Create the customer object
        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        // Store customer in db
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse response = fraudClient.isFraudster(customer.getId());

        assert response != null;
        if(response.isFraudster()){
            throw new IllegalStateException("Customer is a fraudster");
        }
        log.info("Fraud check complete");

        // Send notification
        String subject = "User registration success";
        String message = "Dear " + customer.getFirstName() + "\nYour account has been successfully registered";
        NotificationRequest notificationRequest = NotificationRequest.builder()
                .toCustomerId(customer.getId())
                .toCustomerEmail(customer.getEmail())
                .subject(subject)
                .message(message)
                .build();
        String notificationResponse = notificationClient().sendNotification(notificationRequest);
        log.info(notificationResponse);
    }
}
