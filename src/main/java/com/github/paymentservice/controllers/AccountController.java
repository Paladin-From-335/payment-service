package com.github.paymentservice.controllers;

import com.github.paymentservice.dto.AccountRegDto;
import com.github.paymentservice.entity.Account;
import com.github.paymentservice.exceptions.BadRequest;
import com.github.paymentservice.services.AccountService;
import com.github.paymentservice.utils.TransferObj;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.github.paymentservice.utils.ParseLong.parseLong;

@Slf4j
@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/registration/account")
    public void accountReg(@RequestBody @Valid AccountRegDto payload) {
        if (parseLong(payload.getAccountNum()) == null || payload.getAccountType() == null || payload.getBalance() == null) {
            throw new BadRequest("Wrong data");
        }
        Account account = TransferObj.toAccount(payload);
        accountService.insert(account);
    }
}
