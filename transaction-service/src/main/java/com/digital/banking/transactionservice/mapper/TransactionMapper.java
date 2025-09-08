package com.digital.banking.transactionservice.mapper;

import com.digital.banking.transactionservice.dto.TransactionDto;
import com.digital.banking.transactionservice.model.Transaction;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .timestamp(transaction.getTimestamp())
                .build();
    }

    public static Transaction toEntity(TransactionDto dto) {
        return Transaction.builder()
                .id(dto.getId())
                .accountId(dto.getAccountId())
                .amount(dto.getAmount())
                .type(dto.getType())
                .timestamp(dto.getTimestamp())
                .build();
    }
}
