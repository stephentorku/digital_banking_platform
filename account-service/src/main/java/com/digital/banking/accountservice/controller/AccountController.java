package com.digital.banking.accountservice.controller;

import com.digital.banking.accountservice.dto.AccountDto;
import com.digital.banking.accountservice.model.Account;
import com.digital.banking.accountservice.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/me")
    public ResponseEntity<String> getAccount(Authentication auth) {
        return ResponseEntity.ok("Account access granted to: " + auth.getName());
    }

    @GetMapping("/public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("This endpoint is public.");
    }

    @PostMapping("/create")
    public ResponseEntity<AccountDto> createAccount(
            @RequestBody AccountDto accountDto,
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(accountService.createAccount(accountDto, auth, request));
    }


    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccountDetails(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccountByAccountNumber(accountNumber));
    }

}
