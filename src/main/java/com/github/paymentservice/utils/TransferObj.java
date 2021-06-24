package com.github.paymentservice.utils;

import com.github.paymentservice.dto.PaymentDto;
import com.github.paymentservice.dto.UserRegDto;
import com.github.paymentservice.entity.Payment;
import com.github.paymentservice.entity.User;

public class TransferObj {

    public static User toUser(UserRegDto data) {
        return new User(
                data.getFirstname(),
                data.getLastname()
        );
    }

    public static Payment toPayment(PaymentDto data) {
        return new Payment(
                data.getAmount(),
                data.getReason(),
                data.getTimestamp(),
                data.getSourceAccId(),
                data.getDestAccId()
        );
    }
}
