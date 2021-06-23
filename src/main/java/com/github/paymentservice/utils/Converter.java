package com.github.paymentservice.utils;

import com.github.paymentservice.dto.AccountRegDto;
import com.github.paymentservice.entity.Account;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Converter {

    public List<Account> convertDtoToEntity(List<AccountRegDto> regDtos, Long clientId) {

        List<Account> accounts = new ArrayList<>();

        for (AccountRegDto dto:regDtos) {
          accounts.add(new Account(clientId, dto.getAccountNum(), dto.getAccountType(), dto.getBalance()));
        }
        return accounts;
    }
}
