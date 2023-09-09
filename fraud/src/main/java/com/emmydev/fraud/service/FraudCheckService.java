package com.emmydev.fraud.service;

import com.emmydev.fraud.entity.FraudCheckHistory;
import com.emmydev.fraud.repository.FraudHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public record FraudCheckService(FraudHistoryRepository fraudHistoryRepository) {

    public boolean isFraudulentCustomer(Long customerId){

        FraudCheckHistory fraudCheckHistory = FraudCheckHistory.builder()
                .isFraudster(false)
                .customerId(customerId)
                .createdAt(LocalDateTime.now())
                .build();

        fraudHistoryRepository.save(fraudCheckHistory);
        log.info("Fraud check complete");
        return false;
    }
}
