package com.digital.banking.accountservice.service;

import com.digital.banking.accountservice.dto.AccountDto;
import com.digital.banking.accountservice.mapper.AccountMapper;
import com.digital.banking.accountservice.model.Account;
import com.digital.banking.accountservice.repository.AccountRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    public AccountDto createAccount(AccountDto accountDto, Authentication auth) {
        Account account = AccountMapper.toEntity(accountDto, auth.getName());
        Account saved = accountRepository.save(account);
        return AccountMapper.toDto(saved);
    }

    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.toDto(account);
    }

    public AccountDto getMyAccount(Authentication auth) {
        Account account = accountRepository.findByUserId(auth.getName())
                .orElseThrow(() -> new RuntimeException("No account for this user"));
        return AccountMapper.toDto(account);
    }
}
