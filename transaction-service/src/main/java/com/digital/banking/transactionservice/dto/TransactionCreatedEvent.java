package com.digital.banking.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreatedEvent {
    private String transactionId;
    private String accountNumber;
    private Double amount;
    private String userEmail;
}

