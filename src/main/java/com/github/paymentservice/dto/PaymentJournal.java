package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentJournal {

    private long payerId;

    private long recipientId;

    private long sourceAccId;

    private long destAccId;


}
