package com.digital.banking.transactionservice.client;

import com.digital.banking.transactionservice.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "account-service", url = "http://localhost:8080/api/accounts")
public interface AccountClient {

    @GetMapping("/{accountNumber}")
    AccountDto getAccountByNumber(@PathVariable("accountNumber") String accountNumber);

    @PostMapping("/{accountNumber}/debit")
    AccountDto debit(@PathVariable("accountNumber") String accountNumber,
                     @RequestParam Double amount);

    @PostMapping("/{accountNumber}/credit")
    AccountDto credit(@PathVariable("accountNumber") String accountNumber,
                      @RequestParam Double amount);
}
