package com.digital.banking.accountservice.service;

import com.digital.banking.accountservice.dto.AccountDto;
import com.digital.banking.accountservice.mapper.AccountMapper;
import com.digital.banking.accountservice.model.Account;
import com.digital.banking.accountservice.repository.AccountRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final JwtService jwtService;


    public AccountService(AccountRepository accountRepository, JwtService jwtService) {
        this.accountRepository = accountRepository;
        this.jwtService = jwtService;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    public AccountDto createAccount(AccountDto accountDto, Authentication auth, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");

        Account account = AccountMapper.toEntity(accountDto, userId);
        Account saved = accountRepository.save(account);
        return AccountMapper.toDto(saved);
    }


    public AccountDto getAccountByAccountNumber(String accountNumber, Authentication auth) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        AccountDto accountDto = AccountMapper.toDto(account);
        accountDto.setOwnerEmail(auth.getName());

        return accountDto;
    }

    public AccountDto getMyAccount(Authentication auth) {
        String token = (String) auth.getCredentials();
        Account account = accountRepository.findByUserId(jwtService.extractUserId(token))
                .orElseThrow(() -> new RuntimeException("No account for this user"));
        return AccountMapper.toDto(account);
    }

    @Transactional
    public AccountDto debit(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        return AccountMapper.toDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto credit(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        return AccountMapper.toDto(accountRepository.save(account));
    }

}
