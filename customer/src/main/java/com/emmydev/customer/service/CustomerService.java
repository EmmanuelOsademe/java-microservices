package com.emmydev.customer.service;

import com.emmydev.customer.database.CustomerRepository;
import com.emmydev.customer.entity.Customer;
import com.emmydev.customer.model.CustomerRegistrationRequest;
import com.emmydev.customer.model.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@Service
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate, WebClient webClient) {

    private static final String baseUrl = "http://localhost:8081/api/v1/fraud-check/";
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

        // Check if fraudster
//        FraudCheckResponse response = restTemplate.getForObject(
//                "http://localhost:8081/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class,
//                customer.getId()
//        );

        FraudCheckResponse response = null;

        try {
            response = webClient
                    .get()
                    .uri(baseUrl+"{customerId}", customer.getId())
                    .retrieve()
                    .bodyToMono(FraudCheckResponse.class)
                    .block();
            log.info("Web client successfully used to connect");
        }catch (WebClientResponseException wcre){
            log.error("Error Response Code is {} and Response body is {}", wcre.getStatusCode(), wcre.getResponseBodyAsString());
            log.error("Exception in method retrieving fraud check status ", wcre);
            throw wcre;
        }catch (Exception ex){
            log.error("Exception in method retrieving fraud check status");
            throw ex;
        }

        assert response != null;
        if(response.isFraudster()){
            throw new IllegalStateException("Customer is a fraudster");
        }
        log.info("Fraud check complete");
    }
}
