package com.digital.banking.transactionservice.controller;

import com.digital.banking.transactionservice.dto.TransactionDto;
import com.digital.banking.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto dto) {
        return ResponseEntity.ok(transactionService.createTransaction(dto));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDto>> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getTransactionsByAccountNumber(accountNumber));
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
