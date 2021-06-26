package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class PaymentResult {

    private Long paymentId;

    private String status;
}
