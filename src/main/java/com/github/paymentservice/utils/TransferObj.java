package com.github.paymentservice.utils;

import com.github.paymentservice.dto.UserRegDto;
import com.github.paymentservice.entity.User;

public class TransferObj {

    public static User toUser(UserRegDto data) {
        return new User(
                data.getFirstname(),
                data.getLastname()
        );
    }

//    public static Account toAccount(AccountRegDto data) {
//        return new Account(
//                data.getAccountNum(),
//                data.getAccountType(),
//                data.getBalance()
//        );
//    }
}
