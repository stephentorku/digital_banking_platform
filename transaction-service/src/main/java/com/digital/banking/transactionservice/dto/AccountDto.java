package com.digital.banking.transactionservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String ownerName;
    private Double balance;
    private String status;
}