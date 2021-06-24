package com.github.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private BigDecimal amount;

    private String reason;

    private Date timestamp = new Date();

    private Long sourceAccId;

    private Long destAccId;

    public PaymentDto(BigDecimal amount, String reason, Long sourceAccId, Long destAccId) {
        this.amount = amount;
        this.reason = reason;
        this.sourceAccId = sourceAccId;
        this.destAccId = destAccId;
    }
}
