package com.digital.banking.accountservice.mapper;

import com.digital.banking.accountservice.dto.AccountDto;
import com.digital.banking.accountservice.model.Account;

public class AccountMapper {

    public static AccountDto toDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .build();
    }

    public static Account toEntity(AccountDto dto, String userId) {
        return Account.builder()
                .accountNumber(dto.getAccountNumber())
                .accountType(dto.getAccountType())
                .balance(dto.getBalance())
                .userId(userId)
                .build();
    }
}
