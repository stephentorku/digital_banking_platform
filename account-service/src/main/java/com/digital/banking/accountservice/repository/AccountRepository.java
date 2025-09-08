package com.digital.banking.accountservice.repository;

import com.digital.banking.accountservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(String userId);
    Optional<Account> findByAccountNumber(String accountNumber);
}
