package com.digital.banking.transactionservice.service;

import com.digital.banking.transactionservice.client.AccountClient;
import com.digital.banking.transactionservice.dto.AccountDto;
import com.digital.banking.transactionservice.dto.TransactionDto;
import com.digital.banking.transactionservice.mapper.TransactionMapper;
import com.digital.banking.transactionservice.model.Transaction;
import com.digital.banking.transactionservice.model.TransactionType;
import com.digital.banking.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionDto createTransaction(TransactionDto dto) {
        // 1. Retrieve account info
        AccountDto account = accountClient.getAccountByNumber(dto.getAccountNumber()).getBody();

        if (dto.getType().equals(TransactionType.DEBIT) && account.getBalance() < dto.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        if (dto.getType().equals(TransactionType.DEBIT)) {
            accountClient.debit(dto.getAccountNumber(), dto.getAmount());
        } else {
            accountClient.credit(dto.getAccountNumber(), dto.getAmount());
        }

        Transaction tx = Transaction.builder()
                .accountNumber(dto.getAccountNumber())
                .amount(dto.getAmount())
                .type(dto.getType())
                .timestamp(LocalDateTime.now())
                .build();
        transactionRepository.save(tx);

        return TransactionMapper.toDto(tx);
    }

    public List<TransactionDto> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber)
                .stream().map(TransactionMapper::toDto).collect(Collectors.toList());
    }

    public List<TransactionDto> getAllTransactions() {
        return transactionRepository.findAll()
                .stream().map(TransactionMapper::toDto).collect(Collectors.toList());
    }
}
