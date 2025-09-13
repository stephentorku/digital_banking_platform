package com.digital.banking.transactionservice.dto;

import com.digital.banking.transactionservice.model.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long id;
    private String accountNumber;
    private Double amount;
    private TransactionType type;
    private LocalDateTime timestamp;
}
