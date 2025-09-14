package com.digital.banking.accountservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
    private Long id;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private String ownerEmail;
}
