package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {

    private Long paymentId;

    private String status;
}
