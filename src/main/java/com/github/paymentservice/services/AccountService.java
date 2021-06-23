package com.github.paymentservice.services;

import com.github.paymentservice.entity.Account;
import com.github.paymentservice.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void insert(Account account) {
        accountRepo.save(account);
    }

    public Optional<Account> findById(Account account) {
       return accountRepo.findById(account.getAccountId());
    }
}
